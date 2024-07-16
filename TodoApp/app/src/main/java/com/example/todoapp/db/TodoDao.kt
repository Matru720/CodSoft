package com.example.todoapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.Todo

@Dao
interface TodoDao {

    @Query("SELECT * FROM TODO")
    fun getAllTodo() : LiveData<List<Todo>>

    @Insert
    fun addTodo(todo: Todo)

    @Query("DELETE FROM Todo where id = :id")
    fun deleteTodo(id : Int)

    @Update
    fun updateTodo(todo: Todo)

    @Query("UPDATE Todo SET completed = :completed WHERE id = :id")
    fun toggleTodoCompletion(id: Int, completed: Boolean)
}
