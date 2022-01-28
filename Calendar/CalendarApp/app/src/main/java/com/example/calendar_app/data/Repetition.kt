package com.example.calendar_app.data

enum class Repetition(val ord: Int, val str: String) {
    ONCE(0, "Jednorazowo"),
    DAILY(1, "Codziennie"),
    WEEKLY(2, "Co tydzien"),
    MONTHLY(3, "Co miesiac"),
    ANNUALLY(4, "Co rok");

    companion object {
        private val values = values()
        fun fromOrd(ord: Int): Repetition? {
            return values[ord]
        }
    }
}