package app.trian.tudu.data.sdk.task


import app.trian.tudu.data.DriverFactory
import app.trian.tudu.data.createDatabase
import app.trian.tudu.data.model.TodoModel
import app.trian.tudu.data.model.toModel
import app.trian.tudu.data.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TodoSDK(
    driverFactory: DriverFactory,
) {
    private val db = createDatabase(driverFactory)

    //region todo
    @Throws(
        Exception::class
    )
    suspend fun getListTodoByTask(taskId: String): Flow<Response<List<TodoModel>>> = flow {
        emit(Response.Loading)
        val result = db.todoQueries
            .getListTodoByTask(taskId)
            .executeAsList()
            .map { it.toModel() }
        emit(Response.Result(result))
    }.flowOn(Dispatchers.Default)

    @Throws(
        Exception::class
    )
    suspend fun updateTodoName(
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

    @Throws(
        Exception::class
    )
    suspend fun updateTodoDone(
        todoId: String,
        todoDone: Boolean
    ): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        db.todoQueries.updateTodoDone(
            todoDone = if (todoDone) 1 else 0,
            todoId = todoId
        )
        emit(Response.Result(true))
    }.flowOn(Dispatchers.Default)

    @Throws(
        Exception::class
    )
    suspend fun deleteTodo(
        todoId: String
    ): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        db.todoQueries.deleteTodo(todoId)
        emit(Response.Result(true))
    }.flowOn(Dispatchers.Default)

    @Throws(
        Exception::class
    )
    suspend fun createNewTodo(
        taskId: String,
        todoId: String,
        todoName: String,
        createdAt: String,
    ): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        db.todoQueries.insertTodo(
            todoId = todoId,
            todoName = todoName,
            todoTaskId = taskId,
            todoDone = 0,
            createdAt = createdAt
        )
        emit(Response.Result(true))
    }.flowOn(Dispatchers.Default)
    //end region

}
