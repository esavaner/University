package com.example.l3z1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.io.IOException


class MyArrayAdapter(context: Context, var data: ArrayList<String>) : ArrayAdapter<String>(context, R.layout.list_item, data) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.list_item, parent, false)
        }
        var pieces = data[position].split(" ")
        when(pieces[0]) {
            "house" -> view!!.findViewById<ImageView>(R.id.imageView8).setBackgroundResource(R.drawable.house)
            "work"  -> view!!.findViewById<ImageView>(R.id.imageView8).setBackgroundResource(R.drawable.work)
            "activity" -> view!!.findViewById<ImageView>(R.id.imageView8).setBackgroundResource(R.drawable.activity)
        }
        if(pieces[1] == "true") {
            view!!.findViewById<ImageView>(R.id.imageView9).setBackgroundResource(android.R.drawable.btn_star_big_on)
        }
        else {
            view!!.findViewById<ImageView>(R.id.imageView9).setBackgroundResource(android.R.drawable.btn_star_big_off)
        }
        view!!.findViewById<TextView>(R.id.textView5).text = pieces[2]
        view!!.findViewById<TextView>(R.id.textView6).text = pieces[3]
        var s = ""
        for(i in 4 until pieces.size) {
            try {
                s += " " + pieces[i]
            }
            catch (e: IOException){
            }
        }
        view!!.findViewById<TextView>(R.id.textView8).text = s
        return view
    }
}