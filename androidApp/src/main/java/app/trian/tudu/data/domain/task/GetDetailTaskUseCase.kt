package app.trian.tudu.data.domain.task

import app.trian.tudu.data.model.TaskModel
import app.trian.tudu.data.model.toModel
import app.trian.tudu.data.utils.Response
import app.trian.tudu.sqldelight.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetDetailTaskUseCase @Inject constructor(
    private val db:Database
) {
    operator fun invoke(taskId:String): Flow<Response<TaskModel>> = flow {
        emit(Response.Loading)
        val result = db.taskQueries
            .getTaskById(taskId)
            .executeAsOneOrNull()
            ?: throw NullPointerException("Not found")
        emit(Response.Result(result.toModel()))
    }.flowOn(Dispatchers.Default)
}