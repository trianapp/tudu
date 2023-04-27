package app.trian.tudu.data.model

import app.trian.tudu.base.extensions.Empty
import app.trian.tudu.table.taskCategory.TaskCategory
import com.google.errorprone.annotations.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class TaskCategoryModel(
    @SerialName("taskCategoryId")
    val taskCategoryId: String = String.Empty,
    @SerialName("taskId")
    val taskId: String = String.Empty,
    @SerialName("categoryId")
    val categoryId: String = String.Empty,
)


@Keep
fun TaskCategory.toModel() = TaskCategoryModel(
    taskCategoryId = taskCategoryId,
    taskId = taskId,
    categoryId = categoryId
)
