package com.example.l3z1


import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import java.util.*

@Entity(tableName = "todo")
@TypeConverters(Converters::class)
data class Todo(
    @ColumnInfo(name = "title") var title : String,
    @ColumnInfo(name = "type") var type : String,
    @ColumnInfo(name = "priority") var priority : Boolean,
    @ColumnInfo(name = "time") var time : Date,
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id : Long = 0
)