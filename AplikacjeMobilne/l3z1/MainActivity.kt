package com.example.l3z1

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    var selectedActivity = "house"
    var selectedDate = "2000-01-01"
    var selectedHour ="00:00"
    var sortType = "all"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var shown = false
        var list = ArrayList<String>()
        var slist = ArrayList<String>()
        list.clear()
        slist.clear()
        val myAdapter = MyArrayAdapter(this, list)
        listView.adapter = myAdapter
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
                    slist.removeAt(index)
                    sort(list, slist, sortType)
                    myAdapter.notifyDataSetChanged()
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
                    slist.removeAt(index)
                    slist.add("$selectedActivity " + checkBox1.isChecked + " $selectedDate $selectedHour " + editText1.text)
                    sort(list, slist, sortType)
                    myAdapter.notifyDataSetChanged()
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
                    slist.add("$selectedActivity " + checkBox.isChecked + " $selectedDate $selectedHour " + editText.text)
                    sort(list, slist, sortType)
                    myAdapter.notifyDataSetChanged()
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
                    sort(list, slist, "prio")
                    myAdapter.notifyDataSetChanged()
                    sortType = "prio"
                    popupWindow.dismiss()
                    shown = false
                }
                textHouse1.setOnClickListener {
                    imageViewC.setImageResource(R.drawable.violethouse)
                    sort(list, slist, "house")
                    myAdapter.notifyDataSetChanged()
                    sortType = "house"
                    popupWindow.dismiss()
                    shown = false
                }
                textWork1.setOnClickListener {
                    imageViewC.setImageResource(R.drawable.brownwork)
                    sort(list, slist, "work")
                    myAdapter.notifyDataSetChanged()
                    sortType = "work"
                    popupWindow.dismiss()
                    shown = false
                }
                textActiv1.setOnClickListener {
                    imageViewC.setImageResource(R.drawable.activ)
                    sort(list, slist, "activity")
                    myAdapter.notifyDataSetChanged()
                    sortType = "activity"
                    popupWindow.dismiss()
                    shown = false
                }
                textAll1.setOnClickListener {
                    imageViewC.setImageResource(R.drawable.greyall)
                    sort(list, slist, "all")
                    myAdapter.notifyDataSetChanged()
                    sortType = "all"
                    popupWindow.dismiss()
                    shown = false
                }
                textToday1.setOnClickListener {
                    imageViewC.setImageResource(R.drawable.greentoday)
                    sort(list, slist, "today")
                    myAdapter.notifyDataSetChanged()
                    sortType = "today"
                    popupWindow.dismiss()
                    shown = false
                }
                textTomorrow1.setOnClickListener {
                    imageViewC.setImageResource(R.drawable.bluetomorrow)
                    sort(list, slist, "tomorrow")
                    myAdapter.notifyDataSetChanged()
                    sortType = "tomorrow"
                    popupWindow.dismiss()
                    shown = false
                }
            }
        }

    }

    fun sort(list: ArrayList<String>, slist: ArrayList<String>, type: String) {
        list.clear()
        for (i in 0 until slist.size) {
            var parts = slist[i].split(" ")
            var date1 = LocalDate.parse(parts[2])
            var today = LocalDate.now()
            var compare = date1.compareTo(today)
            when(type) {
                "house" -> if(parts[0] == "house") { list.add(slist[i]) }
                "work" -> if(parts[0] == "work") { list.add(slist[i]) }
                "activity" -> if(parts[0] == "activity") { list.add(slist[i]) }
                "prio" -> if(parts[1] == "true") { list.add(slist[i]) }
                "today" -> {
                    if(compare < 1) {
                        list.add(slist[i])
                    }
                }
                "tomorrow" -> {
                    if(compare in 1..2) {
                        list.add(slist[i])
                    }
                }
                "all" -> list.add(slist[i])
            }
        }
    }

    fun getDate(view: View) {
        var monthS = ""
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