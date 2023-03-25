package app.trian.tudu.data.domain.task

import app.trian.tudu.data.utils.Response
import app.trian.tudu.sqldelight.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDate
import javax.inject.Inject

class UpdateTaskDoneUseCase @Inject constructor(
    private val db: Database
) {
    operator fun invoke(
        taskId: String,
        taskDone: Boolean,
    ): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        db.taskQueries.updateTaskToDone(
            taskDone = if (taskDone) 1 else 0,
            taskId = taskId,
            updatedAt = LocalDate.now().toString()
        )
        emit(Response.Result(true))
    }.flowOn(Dispatchers.Default)
}