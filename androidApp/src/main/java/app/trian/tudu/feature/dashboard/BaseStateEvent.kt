package app.trian.tudu.feature.dashboard

import android.os.Parcelable
import app.trian.tudu.data.model.CategoryModel
import app.trian.tudu.data.model.TodoModel
import java.time.LocalDate
import java.time.LocalTime

abstract class BaseDashboardState:Parcelable  {
    abstract val taskId:String
    abstract val taskName:String
    abstract val categories:List<CategoryModel>
    abstract val todos:List<TodoModel>

    abstract val showDialogPickCategory:Boolean
    abstract val showDialogPickDueDate:Boolean
    abstract val showDialogPickDueTime:Boolean

    abstract val dueDate:LocalDate?
    abstract val dueTime:LocalTime?
}

abstract class BaseDashboardDataState:Parcelable{
    abstract val categories:List<CategoryModel>
    abstract val todos:List<TodoModel>
}