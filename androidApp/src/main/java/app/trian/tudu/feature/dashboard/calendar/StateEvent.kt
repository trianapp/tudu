package app.trian.tudu.feature.dashboard.calendar

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
data class CalendarState(
    var selectedDate: LocalDate = LocalDate.now(),
    override val showDialogPickCategory: Boolean=false,
    override val taskName: String="",
    override val categories:@RawValue List<CategoryModel> = listOf(),
    override val todos:@RawValue List<TodoModel> = listOf(),
    override val dueDate: LocalDate?=null,
    override val dueTime: LocalTime?=null,
    override val taskId: String="",
    override val showDialogPickDueDate: Boolean=false,
    override val showDialogPickDueTime: Boolean=false,
): BaseDashboardState(),Parcelable

@Parcelize
@Immutable
data class CalendarDataState(
    var task:@RawValue List<TaskWithCategory> = listOf(),
    override val categories:@RawValue List<CategoryModel> = listOf(),
    override val todos:@RawValue List<TodoModel> = listOf()
) : BaseDashboardDataState(), Parcelable

@Immutable
sealed class CalendarEvent {

    data class AddPlainTodo(val todoName: String) : CalendarEvent()
    object GetData : CalendarEvent()
    data class GetTask(val date:LocalDate): CalendarEvent()
    data class UpdatePlainTodo(val todo: TodoModel) : CalendarEvent()
    data class DeletePlainTodo(val todoId: String) : CalendarEvent()

    object SubmitTask : CalendarEvent()

}