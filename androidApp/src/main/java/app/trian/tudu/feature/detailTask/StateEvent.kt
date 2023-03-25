package app.trian.tudu.feature.detailTask

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import app.trian.tudu.data.model.CategoryModel
import app.trian.tudu.data.model.TaskModel
import app.trian.tudu.data.model.TodoModel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.time.LocalDate
import java.time.LocalTime

@Parcelize
@Immutable
data class DetailTaskState(
    val titleApp:String="",

    val taskId: String = "",
    val taskName: String="",
    val taskDueDate: LocalDate = LocalDate.now(),
    val taskDueTime: LocalTime = LocalTime.now(),
    val taskReminder: Boolean = false,
    val taskNote: String = "",
    val taskDone:Boolean=false,

    val showDialogPickCategory: Boolean = false,
    val showDialogPickTime: Boolean = false,
    val showDialogPickDate: Boolean = false,
    val showDialogDeleteConfirmation: Boolean = false
) : Parcelable

@Parcelize
@Immutable
data class DetailTaskDataState(
    val task: @RawValue TaskModel = TaskModel(),
    val todos: @RawValue List<TodoModel> = listOf(),
    val allCategories: @RawValue List<CategoryModel> = listOf(),
    val categories: @RawValue List<CategoryModel> = listOf()
) : Parcelable

sealed class DetailTaskEvent {
    object CheckBackPressed : DetailTaskEvent()

    object GetDetailTask: DetailTaskEvent()

    data class UpdateTaskName(val taskName: String) : DetailTaskEvent()
    data class UpdateTaskDueDate(val dueDate: LocalDate) : DetailTaskEvent()
    data class UpdateTaskDueTime(val dueTime: LocalTime) : DetailTaskEvent()
    data class UpdateTaskReminder(val reminder: Boolean) : DetailTaskEvent()
    data class UpdateTaskCategory(val categories: List<CategoryModel>) : DetailTaskEvent()
    data class UpdateTaskDone(val taskDone:Boolean) : DetailTaskEvent()

    object SubmitTask : DetailTaskEvent()
    object DeleteTask : DetailTaskEvent()
    object CreateTodo : DetailTaskEvent()
    data class UpdateTodoDone(val todoId: String,val isDone: Boolean) : DetailTaskEvent()
    data class UpdateTodoName(val todoId: String, val todoName: String) : DetailTaskEvent()
    data class DeleteTodo(val todoId: String) : DetailTaskEvent()

}