package app.trian.tudu.data.repository.design

import app.trian.tudu.data.local.*
import app.trian.tudu.domain.DataState
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun getListTask():Flow<List<Task>>
    suspend fun getTaskById(taskId:String):Flow<DataState<Task>>
    suspend fun createNewTask(task: Task):Flow<DataState<Task>>
    suspend fun updateTask(task: Task):Flow<DataState<Task>>
    suspend fun deleteTask(task: Task):Flow<DataState<Task>>

    suspend fun addTodoToTask(todo: Todo):Flow<DataState<Todo>>
    suspend fun editTodo(todo: Todo):Flow<DataState<Todo>>
    suspend fun deleteTodo(todo: Todo):Flow<DataState<Todo>>

    suspend fun addAttachment(attachment: Attachment):Flow<DataState<Attachment>>
    suspend fun editAttachment(attachment: Attachment):Flow<DataState<Attachment>>
    suspend fun deleteAttachment(attachment: Attachment):Flow<DataState<Attachment>>

    suspend fun addCategory(category: Category):Flow<DataState<Category>>
}