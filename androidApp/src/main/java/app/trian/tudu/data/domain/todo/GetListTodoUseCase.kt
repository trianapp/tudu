package app.trian.tudu.data.domain.todo

import app.trian.tudu.data.model.TodoModel
import app.trian.tudu.data.model.toModel
import app.trian.tudu.data.utils.Response
import app.trian.tudu.sqldelight.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetListTodoUseCase @Inject constructor(
    private val db:Database
) {
    operator fun invoke(
        taskId:String
    ): Flow<Response<List<TodoModel>>> = flow {
        emit(Response.Loading)
        val result = db.todoQueries
            .getListTodoByTask(taskId)
            .executeAsList()
            .map { it.toModel() }
        emit(Response.Result(result))
    }.flowOn(Dispatchers.Default)
}