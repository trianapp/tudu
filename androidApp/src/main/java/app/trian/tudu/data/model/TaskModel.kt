package app.trian.tudu.data.model

import app.trian.tudu.table.task.Task
import com.google.errorprone.annotations.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class TaskModel(
    @SerialName("taskId")
    val taskId: String = "",
    @SerialName("taskName")
    val taskName: String = "",
    @SerialName("taskDueDate")
    val taskDueDate: String = "",
    @SerialName("taskDueTime")
    val taskDueTime: String = "",
    @SerialName("taskDone")
    val taskDone: Boolean = false,
    @SerialName("taskReminder")
    val taskReminder: Boolean = false,
    @SerialName("taskNote")
    val taskNote: String = "",
    @SerialName("isUploaded")
    val isUploaded: Boolean = false,
    @SerialName("createdAt")
    val createdAt: String = "",
    @SerialName("updatedAt")
    val updatedAt: String = ""
)

@Keep
@Serializable
data class TaskWithCategory(
    @SerialName("task")
    val task: TaskModel = TaskModel(),
    @SerialName("category")
    val category: List<CategoryModel> = listOf()
)

@Keep
@Serializable
data class TaskWithCategoryAndTodo(
    @SerialName("task")
    val task: TaskModel = TaskModel(),
    @SerialName("category")
    val category: List<CategoryModel> = listOf(),
    @SerialName("todos")
    val todos: List<TodoModel> = listOf()
)

@Keep
fun Task.toModel() = TaskModel(
    taskId = taskId,
    taskName = taskName,
    taskDueDate = taskDueDate.orEmpty(),
    taskDueTime = taskDueTime.orEmpty(),
    taskDone = taskDone?.toInt() == 1,
    taskReminder = taskReminder?.toInt() == 1,
    taskNote = taskNote.orEmpty(),
    isUploaded = isUploaded?.toInt() == 1,
    createdAt = createdAt.orEmpty(),
    updatedAt = updatedAt.orEmpty()
)