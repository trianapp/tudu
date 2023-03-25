package app.trian.tudu.data.domain.task

import app.trian.tudu.data.utils.Response
import app.trian.tudu.sqldelight.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDate
import javax.inject.Inject

class UpdateTaskDueDateUseCase @Inject constructor(
    private val db:Database
) {
    operator fun invoke(
        taskId:String,
        taskDueDate:LocalDate
    ): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        db.taskQueries.updateTaskDueDate(
            taskId = taskId,
            taskDueDate = taskDueDate.toString(),
            updatedAt = LocalDate.now().toString()
        )
        emit(Response.Result(true))
    }.flowOn(Dispatchers.Default)
}