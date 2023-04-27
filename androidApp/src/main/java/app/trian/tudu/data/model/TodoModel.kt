package app.trian.tudu.data.model

import app.trian.tudu.base.extensions.Empty
import app.trian.tudu.table.todo.Todo
import com.google.errorprone.annotations.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class TodoModel(
    @SerialName("todoId")
    val todoId: String = String.Empty,
    @SerialName("todoName")
    val todoName: String = String.Empty,
    @SerialName("todoDone")
    val todoDone: Boolean = false,
    @SerialName("todoTaskId")
    val todoTaskId: String = String.Empty,
    @SerialName("createdAt")
    val createdAt: String = String.Empty
)

@Keep
fun Todo.toModel() = TodoModel(
    todoId = todoId,
    todoName = todoName.orEmpty(),
    todoDone = todoDone?.toInt() == 1,
    todoTaskId = todoTaskId.orEmpty(),
    createdAt = createdAt.orEmpty()
)