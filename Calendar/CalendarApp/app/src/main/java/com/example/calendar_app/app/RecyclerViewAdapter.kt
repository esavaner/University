package com.example.calendar_app.app

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.calendar_app.R
import java.text.SimpleDateFormat

class RecyclerViewAdapter(private val instances : List<SaveInstance>) : RecyclerView.Adapter<RecyclerViewHolder>() {

    override fun getItemCount() :Int {
        return instances.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RecyclerViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val si: SaveInstance = instances[position]
        holder.bind(si)
    }
}

class RecyclerViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.recyclerview_item, parent, false)) {
    private var titleView: TextView? = null
    private var typeView: TextView? = null
    private var dateView: TextView? = null
    private var localizationView: TextView? = null
    private var typeImage: ImageView? = null

    init {
        titleView = itemView.findViewById(R.id.titleItemView)
        typeView = itemView.findViewById(R.id.typeItemView)
        typeImage = itemView.findViewById(R.id.typeImageView)
        dateView = itemView.findViewById(R.id.dateItemView)
        localizationView = itemView.findViewById(R.id.localizationItemView)
    }

    fun bind(si: SaveInstance) {
        dateView?.text = SimpleDateFormat("yyyy-MM-dd").format(si.time)
        if(si.localization != "") {
            if(si.title != "") {
                titleView?.text = si.title
            } else {
                titleView?.text = "<bez nazwy>"
            }
            typeImage?.setImageResource(R.drawable.eventfull)
            typeView?.text = "Wydarzenie"
            localizationView?.text = si.localization
        } else {
            if(si.title1c != "") {
                titleView?.text = si.title1c
            } else {
                titleView?.text = "<bez nazwy>"
            }
            typeImage?.setImageResource(android.R.drawable.star_big_on)
            typeView?.text = "Odliczanie"
            localizationView?.text = " "
        }
    }
}