package app.trian.tudu.data.repository

import app.trian.tudu.common.DispatcherProvider
import app.trian.tudu.data.local.*
import app.trian.tudu.data.local.dao.AttachmentDao
import app.trian.tudu.data.local.dao.CategoryDao
import app.trian.tudu.data.local.dao.TaskDao
import app.trian.tudu.data.local.dao.TodoDao
import app.trian.tudu.data.repository.design.TaskRepository
import app.trian.tudu.domain.DataState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import logcat.logcat

class TaskRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val taskDao: TaskDao,
    private val todoDao: TodoDao,
    private val attachmentDao: AttachmentDao,
    private val categoryDao: CategoryDao,
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
):TaskRepository {
    companion object{
        const val TASK_COLLECTION = "TASK"
    }
    override suspend fun getListTask(): Flow<List<Task>> = taskDao.getListTask().flowOn(dispatcherProvider.io())

    override suspend fun createNewTask(task: Task): Flow<DataState<Task>> =flow{
        val idFromFireStore = firestore.collection(TASK_COLLECTION).document().id
        val user = firebaseAuth.currentUser
        task.apply {
            taskId=idFromFireStore
            uid = user?.uid ?: ""
        }
        taskDao.insertNewTask(task)
        emit(DataState.onData(task))
    }.flowOn(dispatcherProvider.io())

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

    override suspend fun addCategory(category: Category): Flow<DataState<Category>> = flow {
        categoryDao.insertNewCategory(category)
        emit(DataState.onData(category))
    }.flowOn(dispatcherProvider.io())
}