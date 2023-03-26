package app.trian.tudu.data.domain.task

import app.trian.tudu.data.utils.Response
import app.trian.tudu.sqldelight.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class UpdateTaskNameUseCase @Inject constructor(
    private val db:Database
) {
    operator fun invoke(
        taskId:String,
        taskName:String
    ): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        db.taskQueries.updateTaskName(
            taskId = taskId,
            taskName = taskName,
            updatedAt = LocalDateTime.now().toString()
        )
        emit(Response.Result(true))
    }.flowOn(Dispatchers.Default)
}