package com.example.calendar_app.app

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import com.example.calendar_app.R
import com.example.calendar_app.data.Countdown
import com.example.calendar_app.data.Event
import com.example.calendar_app.data.Reminder
import com.example.calendar_app.data.Repetition
import kotlinx.android.synthetic.main.new_event.*
import java.text.SimpleDateFormat
import java.util.*



class NewEventActivity : AppCompatActivity() {
    var date = Date()
    var mode = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_event)

        //set things from MainActivity intent
        val si = intent.getParcelableExtra<SaveInstance>("instance")
        if (si != null) {
            date = si.time

            //event tab
            titleText.setText(si.title)
            dateText.setText(SimpleDateFormat("yyyy-MM-dd").format(date))
            spinner.setSelection(si.repetition)
            localizationText.setText(si.localization)
            descriptionText.setText(si.description)

            //countdown tab
            titleText1c.setText(si.title1c)
            dateText1c.setText(SimpleDateFormat("yyyy-MM-dd").format(date))
            spinner1c.setSelection(si.reminder1c)
            spinner2c.setSelection(si.repetition1c)
        }

        //restore items from savedInstance
        if (savedInstanceState != null) {
            val si1 = savedInstanceState.getParcelable<SaveInstance>("instance")
            date = si1.time

            //event tab
            titleText.setText(si1.title)
            dateText.setText(SimpleDateFormat("yyyy-MM-dd").format(date))
            spinner.setSelection(si1.repetition)
            localizationText.setText(si1.localization)
            descriptionText.setText(si1.description)

            //countdown tab
            titleText1c.setText(si1.title1c)
            dateText1c.setText(SimpleDateFormat("yyyy-MM-dd").format(date))
            spinner1c.setSelection(si1.reminder1c)
            spinner2c.setSelection(si1.repetition1c)
        }

        //hide countdown layout on start
        constraint2c.visibility = View.GONE

        //adapter for drop down spinners
        val adapter = ArrayAdapter.createFromResource(this, R.array.spinner_list, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner2c.adapter = adapter

        val adapter2 = ArrayAdapter.createFromResource(this, R.array.spinner_list2, android.R.layout.simple_spinner_item)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1c.adapter = adapter2

        //swap between layouts: event
        imageCountdown.setOnClickListener {
            mode = 2
            imageEvent.setImageResource(R.drawable.eventempty)
            imageCountdown.setImageResource(android.R.drawable.star_big_on)
            constraint2.visibility = View.GONE
            constraint3.visibility = View.GONE
            constraint2c.visibility = View.VISIBLE
        }

        //swap between layouts: countdown
        imageEvent.setOnClickListener {
            mode = 1
            imageEvent.setImageResource(R.drawable.eventfull)
            imageCountdown.setImageResource(android.R.drawable.star_big_off)
            constraint2c.visibility = View.GONE
            constraint2.visibility = View.VISIBLE
            constraint3.visibility = View.VISIBLE
        }
    }

    fun confirm(view: View) {
        val intent = Intent()
        if (mode == 1) {
            val e = Event(
                getSharedPreferences("am_calendar_login_data", Context.MODE_PRIVATE).getLong("userID", 0),
                titleText.text.toString(),
                date,
                Repetition.fromOrd(spinner.selectedItemPosition)!!,
                localizationText.text.toString(),
                descriptionText.text.toString()
            )
            intent.putExtra("event", e)
        } else {
            val c = Countdown(
                getSharedPreferences("am_calendar_login_data", Context.MODE_PRIVATE).getLong("userID", 0),
                titleText1c.text.toString(),
                date,
                Reminder.fromOrd(spinner1c.selectedItemPosition)!!,
                Repetition.fromOrd(spinner2c.selectedItemPosition)!!
            )
            intent.putExtra("countdown", c)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun cancel(view: View) {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val si = SaveInstance(
            //event
            titleText.text.toString(),
            date,
            spinner.selectedItemPosition,
            localizationText.text.toString(),
            descriptionText.text.toString(),

            //countdown
            titleText1c.text.toString(),
            spinner1c.selectedItemPosition,
            spinner2c.selectedItemPosition
        )
        outState.putParcelable("instance", si)
    }

    fun changeSelectedDate(view: View) {
        var monthS : String
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, DatePickerDialog.OnDateSetListener{ view, year, monthOfYear, dayOfMonth ->
            if(monthOfYear+1 < 10)
                monthS = "0" + (monthOfYear+1).toString()
            else
                monthS = monthOfYear.toString()
            date = SimpleDateFormat("yyyy-MM-dd").parse("$year-$monthS-$dayOfMonth")
            dateText.setText(SimpleDateFormat("yyyy-MM-dd").format(date))
            dateText1c.setText(SimpleDateFormat("yyyy-MM-dd").format(date))
        }, year, month, day)
        dpd.show()
    }
}

