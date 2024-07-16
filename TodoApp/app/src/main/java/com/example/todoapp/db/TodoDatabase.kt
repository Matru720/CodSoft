package com.example.todoapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.todoapp.Todo
import java.util.jar.Attributes.Name


@Database(entities = [Todo::class], version = 1)
@TypeConverters(Converters::class)
abstract class TodoDatabase: RoomDatabase() {

    companion object{
        const val Name = "Todo_DB"
    }

    abstract fun getTodoDao() : TodoDao
}