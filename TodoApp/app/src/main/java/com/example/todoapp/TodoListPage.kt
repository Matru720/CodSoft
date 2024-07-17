package com.example.todoapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TodoListPage(viewModel: TodoViewModel) {
    val todoList by viewModel.todoList.observeAsState()
    var inputTitle by remember { mutableStateOf("") }
    var inputSubtitle by remember { mutableStateOf("") }
    var editingTodo by remember { mutableStateOf<Todo?>(null) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xFF5B7167),
                        Color(0xFFF2E9E4)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column {
                    TextField(
                        modifier = Modifier
                            .padding(top =15.dp)
                            .background(Color.White)
                            .border(width = 2.dp, color = Color.Black),
                        value = inputTitle,
                        label = {
                            Text(
                                "Task",
                                color = Color.Black,
                                fontFamily = FontFamily(Font(R.font.poppins_regular))
                            )
                        },
                        onValueChange = { inputTitle = it },
                    )
                    TextField(
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .border(width = 2.dp, color = Color.Black),
                        value = inputSubtitle,
                        onValueChange = {
                            inputSubtitle = it
                        },
                        label = {
                            Text(
                                "Description",
                                color = Color.Black,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            )
                        },
                        textStyle = TextStyle(Color.Black)
                    )
                }
                IconButton(
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .size(55.dp),
                    onClick = {
                        if (editingTodo != null) {
                            val updatedTodo =
                                editingTodo!!.copy(title = inputTitle, subtitle = inputSubtitle)
                            viewModel.updateTodo(updatedTodo)
                            editingTodo = null
                        } else {
                            viewModel.addTodo(inputTitle, inputSubtitle)
                        }
                        inputTitle = ""
                        inputSubtitle = ""
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.add_circle_24),
                       // colorFilter = ColorFilter.tint(Color.White),
                        contentDescription = "Add",
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxSize()
                    )
                    //Text(text = if (editingTodo != null) "UPDATE" else "ADD")
                }
            }
            todoList?.let {
                LazyColumn(content = {
                    itemsIndexed(it) { index: Int, item: Todo ->
                        TodoItem(
                            item = item,
                            onDelete = {
                                viewModel.deleteTodo(item.id)
                            },
                            onEdit = {
                                inputTitle = item.title
                                inputSubtitle = item.subtitle
                                editingTodo = item
                            },
                            onToggleCompletion = {
                                viewModel.toggleTodoCompletion(item.id, !item.completed)
                            }
                        )
                    }
                })
            } ?: Text(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "No Tasks",
                fontSize = 20.sp,
                color = Color.Black
            )
        }
    }


}

@Composable
fun TodoItem(item: Todo, onDelete: () -> Unit, onEdit: () -> Unit, onToggleCompletion: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = Modifier.padding(6.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = item.completed,
                onCheckedChange = { onToggleCompletion() }
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title,
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontSize = 20.sp,
                    color = if (item.completed) Color.Gray else Color.Black
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = item.subtitle,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontSize = 13.sp,
                    color = if (item.completed) Color.Gray else Color.Black
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = SimpleDateFormat(
                        "HH:mm:aa, dd/MM",
                        Locale.ENGLISH
                    ).format(item.createdAt),
                    fontSize = 12.sp,
                    color = Color.LightGray
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    painter = painterResource(id = R.drawable.delete_24),
                    contentDescription = "delete",
                    tint = Color.Black
                )
            }
            IconButton(onClick = onEdit) {
                Icon(
                    painter = painterResource(id = R.drawable.edit_24),
                    contentDescription = "edit",
                    tint = Color.Black
                )
            }
        }
    }

}
