package com.example.l3z1

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database( entities = [(Todo::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao() : TodoDAO
}