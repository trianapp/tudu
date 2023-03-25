package app.trian.tudu.data.domain.todo

import app.trian.tudu.data.utils.Response
import app.trian.tudu.sqldelight.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UpdateTodoNameUseCase @Inject constructor(
    private val db:Database
) {
    operator fun invoke(
        todoId: String,
        todoName: String
    ): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        db.todoQueries.updateTodoName(
            todoName = todoName,
            todoId = todoId
        )
        emit(Response.Result(true))
    }.flowOn(Dispatchers.Default)
}