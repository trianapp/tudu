package app.trian.tudu.data.repository.design

import app.trian.tudu.data.local.*
import app.trian.tudu.domain.ChartModelData
import app.trian.tudu.domain.DataState
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime

interface TaskRepository {
    suspend fun getListTask():Flow<List<Task>>
    suspend fun getListTaskByDate(date:OffsetDateTime):Flow<List<Task>>
    suspend fun getListTaskByCategory(categoryId:String):Flow<List<Task>>
    suspend fun getTaskById(taskId:String):Flow<Task?>
    suspend fun getWeekCompleteCount(date:OffsetDateTime):Flow<ChartModelData>
    suspend fun createNewTask(task: Task,todo:List<Todo>):Flow<DataState<Task>>
    suspend fun updateTask(task: Task):Flow<Task>
    suspend fun getBackupTaskFromCloud():Flow<DataState<List<Task>>>
    suspend fun sendBackupTaskToCloud():Flow<DataState<List<Task>>>


    suspend fun getListCompleteTodo(taskId:String):Flow<List<Todo>>
    suspend fun getListUnCompleteTodo(taskId:String):Flow<List<Todo>>
    suspend fun addTodo(todo: Todo):Flow<Todo>
    suspend fun updateTodo(todo: Todo):Flow<Todo>
    suspend fun deleteTodo(todo: Todo):Flow<Todo>

    suspend fun addCategory(category: Category):Flow<DataState<Category>>
    suspend fun getListCategory():Flow<List<Category>>
    suspend fun updateCategory(category: Category):Flow<DataState<Category>>
    suspend fun deleteCategory(category: Category):Flow<DataState<Category>>
}