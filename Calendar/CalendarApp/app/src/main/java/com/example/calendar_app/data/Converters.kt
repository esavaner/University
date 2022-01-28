package com.example.calendar_app.data

import android.arch.persistence.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromInt(value: Int?): Repetition? {
        return when (value) {
            0 -> Repetition.ONCE
            1 -> Repetition.DAILY
            2 -> Repetition.WEEKLY
            3 -> Repetition.MONTHLY
            4 -> Repetition.ANNUALLY
            else -> null
        }
    }

    @TypeConverter
    fun repetitionToInt(value: Repetition): Int {
        return value.ord
    }

    @TypeConverter
    fun fromInt2(value: Int?): Reminder? {
        return when (value) {
            0 -> Reminder.DAY_BEFORE
            1 -> Reminder.THREE_DAYS_BEFORE
            2 -> Reminder.WEEK_BEFORE
            else -> null
        }
    }

    @TypeConverter
    fun reminderToInt(value: Reminder): Int {
        return value.ord
    }
}