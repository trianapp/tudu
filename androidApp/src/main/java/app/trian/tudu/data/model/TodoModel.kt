package app.trian.tudu.data.model

import todo.Todo

data class TodoModel(
    val todoId: String = "",
    val todoName: String = "",
    val todoDone: Boolean = false,
    val todoTaskId: String = "",
    val createdAt: String = ""
)

fun TodoModel.toEntity() = Todo(
    todoId = todoId,
    todoName = todoName,
    todoDone = if (todoDone) 1 else 0,
    todoTaskId = todoTaskId,
    createdAt = createdAt
)

fun Todo.toModel() = TodoModel(
    todoId = todoId,
    todoName = todoName.orEmpty(),
    todoDone = todoDone?.toInt() == 1,
    todoTaskId = todoTaskId.orEmpty(),
    createdAt = createdAt.orEmpty()
)