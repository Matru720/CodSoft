package com.example.todoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class TodoViewModel : ViewModel() {

    val todoDao= MainApplication.todoDatabase.getTodoDao()


    val todoList: LiveData<List<Todo>> = todoDao.getAllTodo()



    fun addTodo(title: String, subtitle: String) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.addTodo(Todo(title=title, subtitle = subtitle, createdAt = Date.from(Instant.now())))
        }

    }

    fun deleteTodo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.deleteTodo(id)
        }
    }
}