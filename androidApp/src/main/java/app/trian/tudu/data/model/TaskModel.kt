package app.trian.tudu.data.model

import app.trian.tudu.table.task.Task
import com.google.errorprone.annotations.Keep

@Keep
data class TaskModel(
    val taskId: String = "",
    val taskName: String = "",
    val taskDueDate: String = "",
    val taskDueTime: String = "",
    val taskDone: Boolean = false,
    val taskReminder: Boolean = false,
    val taskNote: String = "",
    val isUploaded: Boolean = false,
    val createdAt: String = "",
    val updatedAt: String = ""
)

@Keep
data class TaskWithCategory(
    val task: TaskModel = TaskModel(),
    val category: List<CategoryModel> = listOf()
)

@Keep
data class TaskWithCategoryAndTodo(
    val task: TaskModel = TaskModel(),
    val category: List<CategoryModel> = listOf(),
    val todos: List<TodoModel> = listOf()
)

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