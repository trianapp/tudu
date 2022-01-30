package app.trian.tudu.data.repository.design

import app.trian.tudu.data.local.*
import app.trian.tudu.domain.DataState
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun getListTask():Flow<List<Task>>
    suspend fun getTaskById(taskId:String):Flow<DataState<Task>>
    suspend fun createNewTask(task: Task):Flow<DataState<Task>>

    suspend fun getListTodo(taskId:String):Flow<List<Todo>>
    suspend fun addTodo(todo: Todo):Flow<Todo>
    suspend fun updateTodo(todo: Todo):Flow<Todo>
    suspend fun deleteTodo(todo: Todo):Flow<Todo>

    suspend fun addCategory(category: Category):Flow<DataState<Category>>
    suspend fun getListCategory():Flow<List<Category>>
}