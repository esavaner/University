package com.example.l3z1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.text.SimpleDateFormat


class MyArrayAdapter(context: Context, var data: List<Todo>) : ArrayAdapter<Todo>(context, R.layout.list_item, data) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if(view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.list_item, parent, false)
        }
        val t = data[position]
        when(t.type) {
            "house" -> view!!.findViewById<ImageView>(R.id.imageView8).setBackgroundResource(R.drawable.house)
            "work"  -> view!!.findViewById<ImageView>(R.id.imageView8).setBackgroundResource(R.drawable.work)
            "activity" -> view!!.findViewById<ImageView>(R.id.imageView8).setBackgroundResource(R.drawable.activity)
        }
        if(t.priority)
            view!!.findViewById<ImageView>(R.id.imageView9).setBackgroundResource(android.R.drawable.btn_star_big_on)
        else
            view!!.findViewById<ImageView>(R.id.imageView9).setBackgroundResource(android.R.drawable.btn_star_big_off)
        view!!.findViewById<TextView>(R.id.textView5).text = SimpleDateFormat("yyyy-MM-dd").format(t.time)
        view!!.findViewById<TextView>(R.id.textView6).text = SimpleDateFormat("hh:mm").format(t.time)
        view!!.findViewById<TextView>(R.id.textView8).text = t.title
        return view
    }
}