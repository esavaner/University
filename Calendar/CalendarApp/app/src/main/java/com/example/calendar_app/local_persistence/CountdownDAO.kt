package com.example.calendar_app.local_persistence

import android.arch.persistence.room.*
import com.example.calendar_app.data.Countdown
import java.util.*

@Dao
abstract class CountdownDAO {
    @Query("select * from countdown")
    abstract fun getAll() : List<Countdown>

    @Query("select * from countdown where time between :start and :end")
    internal abstract fun getByTimestamp(start : Long, end : Long) : List<Countdown>

    fun getByDay(day: Date) : List<Countdown>
    {
       return getByTimestamp(day.time, day.time + 24*60*60-1)
    }

    @Query("UPDATE countdown SET userID = :userID WHERE userID <> :userID")
    abstract fun fixUserID(userID : Long)

    @Insert
    abstract fun insert(countdown : Countdown)

    @Insert
    abstract fun insertMany(countdowns : List<Countdown>)

    @Update
    abstract fun update(countdown: Countdown)

    @Update
    abstract fun updateMany(countdowns : List<Countdown>)

    @Delete
    abstract fun delete(countdown: Countdown)

    @Delete
    abstract fun deleteMany(countdowns : List<Countdown>)

    // deleteAll should be used to clean up database when new user is about
    // to be logged in.
    @Query("delete from countdown")
    abstract fun deleteAll()
}