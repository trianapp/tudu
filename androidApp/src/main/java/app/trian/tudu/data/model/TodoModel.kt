package app.trian.tudu.data.model

import app.trian.tudu.table.todo.Todo
import com.google.errorprone.annotations.Keep

@Keep
data class TodoModel(
    val todoId: String = "",
    val todoName: String = "",
    val todoDone: Boolean = false,
    val todoTaskId: String = "",
    val createdAt: String = ""
)

fun Todo.toModel() = TodoModel(
    todoId = todoId,
    todoName = todoName.orEmpty(),
    todoDone = todoDone?.toInt() == 1,
    todoTaskId = todoTaskId.orEmpty(),
    createdAt = createdAt.orEmpty()
)