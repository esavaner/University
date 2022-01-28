package com.example.l3z1

import android.arch.persistence.room.Room
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context
import android.os.AsyncTask
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var database: AppDatabase
    var selectedActivity = "house"
    var selectedDate = "2000-01-01"
    var selectedHour ="00:00"
    var sortType = "all"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AsyncTask.execute {
            try {
                database = Room.databaseBuilder(
                    this,
                    AppDatabase::class.java,
                    "student.db"
                ).build()
                sort(sortType)
            } catch (e: Exception) {
            }
        }

        var shown = false
        listView.setOnItemClickListener { _,_, index, _ ->
            if(!shown) {
                shown = true
                val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val view = inflater.inflate(R.layout.edit_todo, null)
                val popupWindow = PopupWindow(
                    view,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                val slideIn = Slide()
                slideIn.slideEdge = Gravity.BOTTOM
                popupWindow.enterTransition = slideIn

                val slideOut = Slide()
                slideOut.slideEdge = Gravity.BOTTOM
                popupWindow.exitTransition = slideOut

                popupWindow.setOnDismissListener {
                    shown = false
                }
                val buttonDelete = view.findViewById<ImageView>(R.id.deleteButton)
                buttonDelete.setOnClickListener {
                    popupWindow.dismiss()
                    shown = false
                    AsyncTask.execute {
                        val d = listView.getItemAtPosition(index) as Todo
                        database.todoDao().delete(d)
                    }
                    sort(sortType)
                }
                TransitionManager.beginDelayedTransition(mainLayout)
                popupWindow.showAtLocation(
                    mainLayout,
                    Gravity.BOTTOM,
                    0,
                    0
                )
                val houseButton = view.findViewById<ImageButton>(R.id.hActivity2)
                val workButton = view.findViewById<ImageButton>(R.id.wActivity2)
                val activityButton = view.findViewById<ImageButton>(R.id.aActivity2)
                houseButton.setOnClickListener {
                    houseButton.setImageResource(R.drawable.house)
                    workButton.setImageResource(R.drawable.workempty)
                    activityButton.setImageResource(R.drawable.activityempty)
                    selectedActivity = "house"
                }
                workButton.setOnClickListener {
                    houseButton.setImageResource(R.drawable.houseempty)
                    workButton.setImageResource(R.drawable.work)
                    activityButton.setImageResource(R.drawable.activityempty)
                    selectedActivity = "work"
                }
                activityButton.setOnClickListener {
                    houseButton.setImageResource(R.drawable.houseempty)
                    workButton.setImageResource(R.drawable.workempty)
                    activityButton.setImageResource(R.drawable.activity)
                    selectedActivity = "activity"
                }
                val confirmButton1 = view.findViewById<ImageButton>(R.id.confirmButton2)
                val checkBox1 = view.findViewById<CheckBox>(R.id.checkBox2)
                val editText1 = view.findViewById<EditText>(R.id.editText2)
                confirmButton1.setOnClickListener {
                    AsyncTask.execute {
                        val d = listView.getItemAtPosition(index) as Todo
                        database.todoDao().delete(d)
                        val t = Todo(editText1.text.toString(), selectedActivity, checkBox1.isChecked, SimpleDateFormat("yyyy-MM-dd-hh:mm").parse("$selectedDate-$selectedHour"))
                        database.todoDao().insertAll(t)
                    }
                    sort(sortType)
                    popupWindow.dismiss()
                    shown = false
                }
                popupWindow.setFocusable(true)
                popupWindow.update()
            }
        }
        addButton.setOnClickListener{
            if(!shown) {
                selectedActivity = "house"
                shown = true
                val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val view = inflater.inflate(R.layout.new_todo, null)
                val popupWindow = PopupWindow(
                    view,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                val slideIn = Slide()
                slideIn.slideEdge = Gravity.BOTTOM
                popupWindow.enterTransition = slideIn

                val slideOut = Slide()
                slideOut.slideEdge = Gravity.BOTTOM
                popupWindow.exitTransition = slideOut

                popupWindow.setOnDismissListener {
                    shown = false
                }
                val buttonCancel = view.findViewById<ImageView>(R.id.cancelButton)
                buttonCancel.setOnClickListener {
                    popupWindow.dismiss()
                    shown = false
                }
                TransitionManager.beginDelayedTransition(mainLayout)
                popupWindow.showAtLocation(
                    mainLayout,
                    Gravity.BOTTOM,
                    0,
                    0
                )
                val houseButton = view.findViewById<ImageButton>(R.id.hActivity)
                val workButton = view.findViewById<ImageButton>(R.id.wActivity)
                val activityButton = view.findViewById<ImageButton>(R.id.aActivity)
                houseButton.setOnClickListener {
                    houseButton.setImageResource(R.drawable.house)
                    workButton.setImageResource(R.drawable.workempty)
                    activityButton.setImageResource(R.drawable.activityempty)
                    selectedActivity = "house"
                }
                workButton.setOnClickListener {
                    houseButton.setImageResource(R.drawable.houseempty)
                    workButton.setImageResource(R.drawable.work)
                    activityButton.setImageResource(R.drawable.activityempty)
                    selectedActivity = "work"
                }
                activityButton.setOnClickListener {
                    houseButton.setImageResource(R.drawable.houseempty)
                    workButton.setImageResource(R.drawable.workempty)
                    activityButton.setImageResource(R.drawable.activity)
                    selectedActivity = "activity"
                }
                val confirmButton = view.findViewById<ImageButton>(R.id.confirmButton)
                val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
                val editText = view.findViewById<EditText>(R.id.editText)
                confirmButton.setOnClickListener {
                    val t = Todo(editText.text.toString(), selectedActivity, checkBox.isChecked, SimpleDateFormat("yyyy-MM-dd-hh:mm").parse("$selectedDate-$selectedHour"))
                    AsyncTask.execute {
                        database.todoDao().insertAll(t)
                        sort(sortType)
                    }
                    popupWindow.dismiss()
                    shown = false
                }
                popupWindow.setFocusable(true)
                popupWindow.update()
            }
        }
        sortButton.setOnClickListener{
            if(!shown) {
                shown = true
                val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val view = inflater.inflate(R.layout.sort_category, null)
                val popupWindow = PopupWindow(
                    view,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                val slideIn = Slide()
                slideIn.slideEdge = Gravity.LEFT
                popupWindow.enterTransition = slideIn

                val slideOut = Slide()
                slideOut.slideEdge = Gravity.LEFT
                popupWindow.exitTransition = slideOut

                val buttonPopup = view.findViewById<ImageView>(R.id.imageView10)
                buttonPopup.setOnClickListener {
                    popupWindow.dismiss()
                    shown = false
                }
                TransitionManager.beginDelayedTransition(mainLayout)
                popupWindow.showAtLocation(
                    mainLayout,
                    Gravity.TOP or Gravity.LEFT,
                    0,
                    0
                )
                val textPrio1 = view.findViewById<TextView>(R.id.textPrio)
                val textHouse1 = view.findViewById<TextView>(R.id.textHouse)
                val textWork1 = view.findViewById<TextView>(R.id.textWork)
                val textActiv1 = view.findViewById<TextView>(R.id.textActiv)
                val textAll1 = view.findViewById<TextView>(R.id.textAll)
                val textToday1 = view.findViewById<TextView>(R.id.textToday)
                val textTomorrow1 = view.findViewById<TextView>(R.id.textTomorrow)
                textPrio1.setOnClickListener {
                    imageViewC.setImageResource(R.drawable.repprio)
                    sortType = "prio"
                    sort(sortType)
                    popupWindow.dismiss()
                    shown = false
                }
                textHouse1.setOnClickListener {
                    imageViewC.setImageResource(R.drawable.violethouse)
                    sortType = "house"
                    sort(sortType)
                    popupWindow.dismiss()
                    shown = false
                }
                textWork1.setOnClickListener {
                    imageViewC.setImageResource(R.drawable.brownwork)
                    sortType = "work"
                    sort(sortType)
                    popupWindow.dismiss()
                    shown = false
                }
                textActiv1.setOnClickListener {
                    imageViewC.setImageResource(R.drawable.activ)
                    sortType = "activity"
                    sort(sortType)
                    popupWindow.dismiss()
                    shown = false
                }
                textAll1.setOnClickListener {
                    imageViewC.setImageResource(R.drawable.greyall)
                    sortType = "all"
                    sort(sortType)
                    popupWindow.dismiss()
                    shown = false
                }
                textToday1.setOnClickListener {
                    imageViewC.setImageResource(R.drawable.greentoday)
                    sortType = "today"
                    sort(sortType)
                    popupWindow.dismiss()
                    shown = false
                }
                textTomorrow1.setOnClickListener {
                    imageViewC.setImageResource(R.drawable.bluetomorrow)
                    sortType = "tomorrow"
                    sort(sortType)
                    popupWindow.dismiss()
                    shown = false
                }
            }
        }

    }

    private fun sort(type: String) {
        val date = Calendar.getInstance().time
        AsyncTask.execute {
            var list = database.todoDao().getAll()
            when (type) {
                "house" -> list = database.todoDao().getHouse()
                "work" -> list = database.todoDao().getWork()
                "activity" -> list = database.todoDao().getActivity()
                "prio" -> list = database.todoDao().getPrio()
                "today" -> list = database.todoDao().getByDay(date)
                "tomorrow" -> list = database.todoDao().getTomorrow(date)
            }
            runOnUiThread {
                val adapter = MyArrayAdapter(this, list)
                listView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }
    }

    fun getDate(view: View) {
        var monthS: String
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view, year, monthOfYear, dayOfMonth ->
            if(monthOfYear+1 < 10)
                monthS = "0" + (monthOfYear+1).toString()
            else
                monthS = monthOfYear.toString()
            selectedDate = ("$year-$monthS-$dayOfMonth")
        }, year, month, day)
        dpd.show()
    }

    fun getTime(view: View) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR)
        val minute = c.get(Calendar.MINUTE)
        val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{view, h, m -> selectedHour = "$h:$m"
        }, hour, minute, true)
        tpd.show()
    }
}