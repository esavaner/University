package com.example.calendar_app.local_persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.calendar_app.data.Countdown
import com.example.calendar_app.data.Event

@Database(entities = [(Event::class), (Countdown::class)], version = 9)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao() : EventDAO
    abstract fun countdownDao() : CountdownDAO
}