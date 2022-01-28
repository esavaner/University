package com.example.calendar_app.app

import android.app.Activity
import android.arch.persistence.room.Room
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.Toast
import com.example.calendar_app.R
import com.example.calendar_app.data.Countdown
import com.example.calendar_app.data.Event
import com.example.calendar_app.local_persistence.AppDatabase
import com.example.calendar_app.network.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var database: AppDatabase
    private var selectedDate = "1970-01-01"
    override fun onStart() {
        super.onStart()
        checkForLoggedIn()
    }

    fun refreshDayEvents(date: Date) {
        AsyncTask.execute {
            val eventsAll = database.eventDao().getByDay(date)
            val countdownsAll = database.countdownDao().getByDay(date)
            val instances = mergeLists(eventsAll, countdownsAll)
            runOnUiThread {
                val recyclerViewAdapter = RecyclerViewAdapter(instances)
                recyclerView.adapter = recyclerViewAdapter
                recyclerViewAdapter.notifyDataSetChanged()
                Log.d("am_calendar_display", "Recycler refreshed")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendar = Calendar.getInstance()

        selectedDate = SimpleDateFormat("yyyy-MM-dd").format(calendar.time)

        AsyncTask.execute {

            try {
                database = Room.databaseBuilder(
                    this,
                    AppDatabase::class.java,
                    "calendar.db"
                ).fallbackToDestructiveMigration()
                    .build()
            } catch (e: Exception) {
                Log.i("ourApp", e.message)
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        refreshDayEvents(SimpleDateFormat("yyyy-MM-dd").parse(selectedDate))

        val calendarView = findViewById<CalendarView>(R.id.calendar_view)
        calendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Base listener
            // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
            //val msg = "Selected date is " + dayOfMonth + "/" + (month + 1) + "/" + year
            selectedDate = "$year-${month + 1}-$dayOfMonth"
            AsyncTask.execute {
                refreshDayEvents(SimpleDateFormat("yyyy-MM-dd").parse(selectedDate))
            }
            //Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun mergeLists(events: List<Event>, countowns: List<Countdown>): List<SaveInstance> {
        val instancesByDay: ArrayList<SaveInstance> = ArrayList()
        events.forEach {
            val si =
                SaveInstance(
                    it.title,
                    it.time,
                    0,
                    it.localization,
                    it.description,
                    "",
                    0,
                    0
                )
            instancesByDay.add(si)
        }
        countowns.forEach {
            val si = SaveInstance("", it.time, 0, "", "", it.title, 0, 0)
            instancesByDay.add(si)
        }
        return instancesByDay
    }

    fun login(view: View) {
        // Display LoginActivity or log user out if already logged in.
        val sharedPref = getSharedPreferences(
            "am_calendar_login_data", Context.MODE_PRIVATE
        )
        val loggedIn = sharedPref.getBoolean("loggedIn", false)

        if (loggedIn) {
            // Remove login data from sharedPref.
            with(sharedPref.edit()) {
                putBoolean("loggedIn", false)
                putString("token", "")
                putLong("userID", -1)
                commit()
            }

            // Remove user data from DB.
            AsyncTask.execute {
                this.database.countdownDao().deleteAll()
                this.database.eventDao().deleteAll()
            }
            checkForLoggedIn()
        } else {
            // Start LoginActivity and stuff.
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkForLoggedIn() {
        // check whether logged in and change all widgets to be suitable.
        val prefs = getSharedPreferences(
            "am_calendar_login_data", Context.MODE_PRIVATE
        )

        if (prefs.getBoolean("loggedIn", false)) {
            findViewById<Button>(R.id.loginButton).text = "Wyloguj"
            findViewById<Button>(R.id.syncButton).isClickable = true
            findViewById<Button>(R.id.syncButton).isEnabled = true

        } else {
            findViewById<Button>(R.id.loginButton).text = "Zaloguj"
            findViewById<Button>(R.id.syncButton).isClickable = false
            findViewById<Button>(R.id.syncButton).isEnabled = false
        }
    }

    fun sync(view: View) {
        AsyncTask.execute {
            // This function should not be called without user loggedIn.
            val prefs = getSharedPreferences(
                "am_calendar_login_data", Context.MODE_PRIVATE
            )

            // Fix userID to match currently logged in user.
            database.eventDao().fixUserID(prefs.getLong("userID", -1))
            database.countdownDao().fixUserID(prefs.getLong("userID", -1))

            val allEvents = database.eventDao().getAll()
            val allCountdowns = database.countdownDao().getAll()
            Log.d("am_calendar_sync", allEvents.toString())
            Log.d("am_calendar_sync", allCountdowns.toString())

            // TODO: change all events and countdowns userIDs to current user ID.
            // It is important if user logged in and events were already added,
            // userID was not known ATM of creating events.
            val saveEvents =
                RetrofitClient.getAPI().saveEvents(prefs.getString("token", ""), allEvents)
            val saveCountdowns =
                RetrofitClient.getAPI()
                    .saveCountdowns(prefs.getString("token", ""), allCountdowns)

            val resultSaveEvents = saveEvents.execute()
            if (resultSaveEvents.code() != 201) {
                Log.e("am_calendar_sync", "Incorrect saveEvents")
                Log.d("am_calendar_sync", resultSaveEvents.message())
                Log.d("am_calendar_sync", resultSaveEvents.code().toString())
                runOnUiThread {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
                }
                return@execute
            }

            val resultSaveCountdowns = saveCountdowns.execute()
            if (resultSaveCountdowns.code() != 201) {
                Log.e("am_calendar_sync", "Incorrect saveCountdowns")
                Log.d("am_calendar_sync", resultSaveCountdowns.message())
                Log.d("am_calendar_sync", resultSaveCountdowns.code().toString())
                runOnUiThread {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
                }
                return@execute
            }

            // I am not sure if it's needed at all?
            database.eventDao().deleteAll()
            database.countdownDao().deleteAll()
            Log.i("am_calendar_sync", database.eventDao().getAll().toString())
            Log.i("am_calendar_sync", database.countdownDao().getAll().toString())

            val getCountdowns = RetrofitClient.getAPI()
                .getCountdowns(prefs.getString("token", ""))
            val resultGetCountdowns = getCountdowns.execute()

            if (resultGetCountdowns.code() != 200) {
                Log.e("am_calendar_sync", "Incorrect getCountdowns")
                Log.d("am_calendar_sync", resultGetCountdowns.message())
                Log.d("am_calendar_sync", resultGetCountdowns.code().toString())
                runOnUiThread {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
                }
                return@execute
            } else {
                // Successful GET
                database.countdownDao().insertMany(resultGetCountdowns.body()!!)
                Log.d("am_calendar_sync", "Countdowns synced")
            }

            val getEvents = RetrofitClient.getAPI().getEvents(prefs.getString("token", ""))
            val resultGetEvents = getEvents.execute()

            if (resultGetEvents.code() != 200) {
                Log.e("am_calendar_sync", "Incorrect getEvents")
                Log.d("am_calendar_sync", resultGetEvents.message())
                Log.d("am_calendar_sync", resultGetEvents.code().toString())
                runOnUiThread {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
                }
                return@execute
            } else {
                database.eventDao().insertMany(resultGetEvents.body()!!)
                Log.i("am_calendar_sync", "Events synced")
            }

            Log.d("am_calendar_sync", database.eventDao().getAll().toString())
            Log.d("am_calendar_sync", database.countdownDao().getAll().toString())
            runOnUiThread {
                Toast.makeText(this, "Successfully synced!", Toast.LENGTH_LONG).show()
            }
            refreshDayEvents(SimpleDateFormat("yyyy-MM-dd").parse(selectedDate))
        }
    }

    fun startNewEventActivity(view: View) {
        val intent = Intent(this, NewEventActivity::class.java)
        val date = SimpleDateFormat("yyyy-MM-dd").parse(selectedDate)
        val si = SaveInstance(
            "",
            date,
            0,
            "",
            "",

            "",
            0,
            0
        )
        intent.putExtra("instance", si)

        startActivityForResult(intent, 123)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 123) {
            if (resultCode == Activity.RESULT_OK) {
                data!!
                val event = data.extras.get("event")
                if (event == null) {
                    val countdown = data.extras.get("countdown")
                    AsyncTask.execute {
                        database.countdownDao().insert(countdown as Countdown)
                        refreshDayEvents(SimpleDateFormat("yyyy-MM-dd").parse(selectedDate))
                    }
                } else {
                    AsyncTask.execute {
                        database.eventDao().insert(event as Event)
                        refreshDayEvents(SimpleDateFormat("yyyy-MM-dd").parse(selectedDate))
                    }
                }
            }
        }
    }
}