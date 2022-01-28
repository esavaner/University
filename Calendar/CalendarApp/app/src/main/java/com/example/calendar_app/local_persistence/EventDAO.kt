package com.example.calendar_app.local_persistence

import android.arch.persistence.room.*
import com.example.calendar_app.data.Event
import java.util.*

@Dao
abstract class EventDAO {
    @Query("select * from event")
    abstract fun getAll() : List<Event>

    @Query("select * from event where time between :start and :end")
    abstract fun getByTimestamp(start : Long, end : Long) : List<Event>

    fun getByDay(day: Date) : List<Event>
    {
       return getByTimestamp(day.time, day.time + 24*60*60-1)
    }

    @Query("UPDATE event SET userID = :userID WHERE userID <> :userID")
    abstract fun fixUserID(userID : Long)

    @Insert
    abstract fun insert(event : Event)

    @Insert
    abstract fun insertMany(events : List<Event>)

    @Update
    abstract fun update(event: Event)

    @Update
    abstract fun updateMany(events : List<Event>)

    @Delete
    abstract fun delete(event: Event)

    @Delete
    abstract fun deleteMany(events : List<Event>)

    // deleteAll should be used to clean up database when new user is about
    // to be logged in.
    @Query("delete from event")
    abstract fun deleteAll()
}