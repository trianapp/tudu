package app.trian.tudu.data.domain.todo

import app.trian.tudu.data.utils.Response
import app.trian.tudu.sqldelight.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

class CreateTodoUseCase @Inject constructor(
    private val db: Database
) {
    operator fun invoke(
        taskId: String,
        todoName: String
    ): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        db.todoQueries.insertTodo(
            todoId = UUID.randomUUID().toString(),
            todoName = todoName,
            todoTaskId = taskId,
            todoDone = 0,
            createdAt = LocalDate.now().toString()
        )
        emit(Response.Result(true))
    }.flowOn(Dispatchers.Default)
}