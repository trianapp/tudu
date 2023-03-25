package app.trian.tudu.data.domain.task

import app.trian.tudu.data.utils.Response
import app.trian.tudu.sqldelight.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalTime
import javax.inject.Inject

class UpdateTaskDueTimeUseCase @Inject constructor(
    private val db: Database
) {
    operator fun invoke(
        taskId: String,
        taskDueTime: LocalTime
    ): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        db.taskQueries.updateTaskDueTime(
            taskId = taskId,
            taskDueTime = taskDueTime.toString(),
            updatedAt = LocalTime.now().toString()
        )
        emit(Response.Result(true))
    }.flowOn(Dispatchers.Default)
}