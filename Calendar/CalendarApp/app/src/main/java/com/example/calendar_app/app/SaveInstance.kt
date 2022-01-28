package com.example.calendar_app.app

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class SaveInstance(
    //event
    var title : String,
    var time : Date,
    var repetition: Int,
    var localization : String,
    var description : String,

    //countdown
    var title1c : String,
    var reminder1c : Int,
    var repetition1c : Int
) : Parcelable