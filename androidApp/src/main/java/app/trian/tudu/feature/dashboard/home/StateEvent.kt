package app.trian.tudu.feature.dashboard.home

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import app.trian.tudu.data.model.CategoryModel
import app.trian.tudu.data.model.TaskWithCategory
import app.trian.tudu.data.model.TodoModel
import app.trian.tudu.feature.dashboard.BaseDashboardDataState
import app.trian.tudu.feature.dashboard.BaseDashboardState
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.time.LocalDate
import java.time.LocalTime

@Parcelize
@Immutable
data class HomeState(
    val showDialogDeleteTask: Boolean = false,
    val showDropdownMoreOption: Boolean = false,
    val isLoading:Boolean=false,
    val message:String="Sync...",
    override val hasCategory: Boolean = false,
    override val hasDueDate: Boolean = false,
    override val hasDueTime: Boolean = false,
    override val hasTodos: Boolean = false,
    override val showDialogPickCategory: Boolean = false,
    override val taskName: String = "",
    override val categories: @RawValue List<CategoryModel> = listOf(),
    override val todos: @RawValue List<TodoModel> = listOf(),
    override val dueDate: LocalDate? = null,
    override val dueTime: LocalTime? = null,
    override val taskId: String = "",
) : BaseDashboardState(), Parcelable

@Parcelize
@Immutable
data class HomeDataState constructor(
    val task: @RawValue List<TaskWithCategory> = listOf(),
    val filteredTask: @RawValue List<TaskWithCategory> = listOf(),
    override val categories:@RawValue List<CategoryModel> = listOf(),
    override val todos:@RawValue List<TodoModel> = listOf()
) : BaseDashboardDataState(), Parcelable

@Immutable
sealed class HomeEvent {
    object SyncTask:HomeEvent()
    data class AddPlainTodo(val todoName: String) : HomeEvent()
    object GetData : HomeEvent()
    data class UpdatePlainTodo(val todo: TodoModel) : HomeEvent()
    data class DeletePlainTodo(val todoId: String) : HomeEvent()
    object DeleteTask : HomeEvent()
    object SubmitTask : HomeEvent()
    data class DoneTask(val isDone: Boolean, val taskId: String) : HomeEvent()
    data class FilterTask(val categoryId:String) : HomeEvent()
}