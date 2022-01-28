package com.example.calendar_app.data

enum class Reminder(val ord: Int, val str: String) {
    DAY_BEFORE(0, "Dzien wczesniej"),
    THREE_DAYS_BEFORE(1, "Trzy dni przed"),
    WEEK_BEFORE(2, "Tydzien przed");

    companion object {
        private val values = values()
        fun fromOrd(ord: Int): Reminder? {
            return values[ord]
        }
    }
}

