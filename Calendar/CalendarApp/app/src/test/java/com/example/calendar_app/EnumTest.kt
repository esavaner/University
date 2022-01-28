package com.example.calendar_app

import com.example.calendar_app.data.Reminder
import com.example.calendar_app.data.Repetition
import org.junit.Test

class EnumTest {
    @Test
    fun fromOrdTest() {
        assert(Reminder.fromOrd(0) == Reminder.DAY_BEFORE)
        assert(Reminder.fromOrd(1) == Reminder.THREE_DAYS_BEFORE)
        assert(Reminder.fromOrd(2) == Reminder.WEEK_BEFORE)
        assert(Repetition.fromOrd(0) == Repetition.ONCE)
        assert(Repetition.fromOrd(1) == Repetition.DAILY)
        assert(Repetition.fromOrd(2) == Repetition.WEEKLY)
        assert(Repetition.fromOrd(3) == Repetition.MONTHLY)
        assert(Repetition.fromOrd(4) == Repetition.ANNUALLY)
    }
}