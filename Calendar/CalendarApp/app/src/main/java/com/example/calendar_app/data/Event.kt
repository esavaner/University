package com.example.calendar_app.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "event")
@TypeConverters(Converters::class)
@Parcelize
data class Event(
    @ColumnInfo(name = "userID") var userID: Long,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "time") var time: Date,
    @ColumnInfo(name = "repetition") var repetition: Repetition,
    @ColumnInfo(name = "localization") var localization: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0
) :
    Parcelable