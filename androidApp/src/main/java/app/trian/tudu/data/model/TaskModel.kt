package app.trian.tudu.data.model

import task.Task

data class TaskModel(
    val taskId: String = "",
    val taskName: String = "",
    val taskDueDate: String = "",
    val taskDueTime: String = "",
    val taskDone: Boolean = false,
    val taskDoneAt: String = "",
    val taskReminder: Boolean = false,
    val taskNote: String = "",
    val createdAt: String = "",
    val updatedAt: String = ""
)

data class TaskWithCategory(
    val task: TaskModel = TaskModel(),
    val category:List<CategoryModel> = listOf()
)


fun TaskModel.toEntity() = Task(
    taskId = taskId,
    taskName = taskName,
    taskDueDate = taskDueDate,
    taskDueTime = taskDueTime,
    taskDone = if (taskDone) 1 else 0,
    taskDoneAt = taskDoneAt,
    taskReminder = if (taskReminder) 1 else 0,
    taskNote = taskNote,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Task.toModel() = TaskModel(
    taskId = taskId,
    taskName = taskName,
    taskDueDate = taskDueDate.orEmpty(),
    taskDueTime = taskDueTime.orEmpty(),
    taskDone = taskDone?.toInt() == 1,
    taskDoneAt = taskDoneAt.orEmpty(),
    taskReminder = taskReminder?.toInt() == 1,
    taskNote = taskNote.orEmpty(),
    createdAt = createdAt.orEmpty(),
    updatedAt = updatedAt.orEmpty()
)