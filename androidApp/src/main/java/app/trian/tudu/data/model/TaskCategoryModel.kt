package app.trian.tudu.data.model

import app.trian.tudu.table.taskCategory.TaskCategory
import com.google.errorprone.annotations.Keep

@Keep
data class TaskCategoryModel(
    val taskCategoryId: String = "",
    val taskId: String = "",
    val categoryId: String = "",
)


@Keep
fun TaskCategory.toModel() = TaskCategoryModel(
    taskCategoryId = taskCategoryId,
    taskId = taskId,
    categoryId = categoryId
)
