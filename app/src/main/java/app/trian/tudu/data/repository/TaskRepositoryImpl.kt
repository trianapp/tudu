package app.trian.tudu.data.repository

import app.trian.tudu.data.local.Attachment
import app.trian.tudu.data.local.Task
import app.trian.tudu.data.local.Todo
import app.trian.tudu.data.local.dao.AttachmentDao
import app.trian.tudu.data.local.dao.TaskDao
import app.trian.tudu.data.local.dao.TodoDao
import app.trian.tudu.data.repository.design.TaskRepository
import app.trian.tudu.domain.DataState
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val taskDao: TaskDao,
    private val todoDao: TodoDao,
    private val attachmentDao: AttachmentDao
):TaskRepository {
    override suspend fun createNewTask(): Flow<DataState<Task>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(task: Task): Flow<DataState<Task>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(task: Task): Flow<DataState<Task>> {
        TODO("Not yet implemented")
    }

    override suspend fun addTodoToTask(todo: Todo): Flow<DataState<Todo>> {
        TODO("Not yet implemented")
    }

    override suspend fun editTodo(todo: Todo): Flow<DataState<Todo>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTodo(todo: Todo): Flow<DataState<Todo>> {
        TODO("Not yet implemented")
    }

    override suspend fun addAttachment(attachment: Attachment): Flow<DataState<Attachment>> {
        TODO("Not yet implemented")
    }

    override suspend fun editAttachment(attachment: Attachment): Flow<DataState<Attachment>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAttachment(attachment: Attachment): Flow<DataState<Attachment>> {
        TODO("Not yet implemented")
    }
}