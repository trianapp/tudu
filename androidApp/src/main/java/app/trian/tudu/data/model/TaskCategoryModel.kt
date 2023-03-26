package app.trian.tudu.data.model

import taskCategory.TaskCategory

data class TaskCategoryModel(
    val taskCategoryId: String = "",
    val taskId: String = "",
    val categoryId: String = "",
)


fun TaskCategory.toModel() = TaskCategoryModel(
    taskCategoryId = taskCategoryId,
    taskId = taskId,
    categoryId = categoryId
)

fun TaskCategory.toEntity() = TaskCategory(
    taskCategoryId = taskCategoryId,
    taskId = taskId,
    categoryId = categoryId
)

