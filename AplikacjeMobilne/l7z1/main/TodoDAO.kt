package com.example.l3z1

import android.arch.persistence.room.*
import java.util.*

@Dao
abstract class TodoDAO {
    @Query("select * from todo")
    abstract fun getAll() : List<Todo>

    @Query("select * from todo where priority = 1")
    abstract fun getPrio() : List<Todo>

    @Query("select * from todo where type = 'house'")
    abstract fun getHouse() : List<Todo>

    @Query("select * from todo where type = 'work'")
    abstract fun getWork() : List<Todo>

    @Query("select * from todo where type = 'activity'")
    abstract fun getActivity() : List<Todo>

    @Query("select * from todo where time between :start and :end")
    abstract fun getByTimestamp(start : Long, end : Long) : List<Todo>

    @Query("select * from todo where title = :s and type = :t and time = :d")
    abstract fun getId(s: String, t: String, d: Long) : Todo

    fun getByDay(day: Date) : List<Todo> {
        return getByTimestamp(day.time, day.time + 24*60*60-1)
    }

    fun getTomorrow(day: Date) : List<Todo> {
        return getByTimestamp(day.time + 24*60*60-1, day.time + 2*24*60*60-1)
    }

    @Insert
    abstract fun insertAll(vararg todo : Todo)

    @Update
    abstract fun update(todo: Todo)

    @Delete
    abstract fun delete(todo: Todo)

    @Query("delete from todo")
    abstract fun deleteAll()
}
