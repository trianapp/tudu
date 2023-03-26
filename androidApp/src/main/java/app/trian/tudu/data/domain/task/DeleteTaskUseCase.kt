package app.trian.tudu.data.domain.task

import app.trian.tudu.data.utils.Response
import app.trian.tudu.sqldelight.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val db:Database
) {
    operator fun invoke(
        taskId:String
    ): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        db.transaction {
            db.taskQueries.deleteTask(taskId)
            db.taskCategoryQueries.deleteTaskCategoryByTask(taskId)
            db.todoQueries.deleteTodoByTask(taskId)
        }
        emit(Response.Result(true))
    }.flowOn(Dispatchers.Default)
}