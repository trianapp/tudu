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
import logcat.LogPriority
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
        const val CATEGORY_COLLECTION = "CATEGORY"
        const val TODO_COLLECTION = "TODO"
    }
    override suspend fun getListTask(): Flow<List<Task>> = taskDao.getListTask().flowOn(dispatcherProvider.io())
    override suspend fun getListTaskByCategory(categoryId: String): Flow<List<Task>> =taskDao.getListTaskByCategory(categoryId).flowOn(dispatcherProvider.io())

    override suspend fun getTaskById(taskId: String): Flow<Task?> = flow {
        val task = taskDao.getTaskById(taskId)
            emit(task)

    }.flowOn(dispatcherProvider.io())


    override suspend fun createNewTask(task: Task,todo:List<Todo>): Flow<DataState<Task>> =flow{

        //get id before inserting into database
        val idFromFireStore = firestore.collection(TASK_COLLECTION).document().id
        val user = firebaseAuth.currentUser
        task.apply {
            taskId=idFromFireStore
            uid = user?.uid ?: ""
        }
        taskDao.insertNewTask(task)
        //after insert task start insert todos if there exist
        if(todo.isNotEmpty()){
            val todos = todo.map {
                it.apply {
                    task_id = idFromFireStore
                }

            }
            todoDao.insertBatchTodo(todos)
        }
        emit(DataState.OnData(task))
    }.flowOn(dispatcherProvider.io())

    override suspend fun updateTask(task: Task): Flow<Task> =flow {
        taskDao.updateTask(task)
        emit(task)
    }.flowOn(dispatcherProvider.io())

    override suspend fun getListCompleteTodo(taskId: String): Flow<List<Todo>> = todoDao.getListCompleteTodoByTask(taskId,true).flowOn(dispatcherProvider.io())
    override suspend fun getListUnCompleteTodo(taskId: String): Flow<List<Todo>> = todoDao.getListUnCompleteTodoByTask(taskId,false).flowOn(dispatcherProvider.io())

    override suspend fun addTodo(todo: Todo): Flow<Todo> =flow{
        todoDao.insertTodoTask(todo)

        emit(todo)
    }.flowOn(dispatcherProvider.io())

    override suspend fun updateTodo(todo: Todo): Flow<Todo> = flow{
        todoDao.updateTodoTask(todo)
        emit(todo)
    }.flowOn(dispatcherProvider.io())

    override suspend fun deleteTodo(todo: Todo): Flow<Todo> = flow{
        todoDao.deleteTodoTask(todo)
        emit(todo)
    }.flowOn(dispatcherProvider.io())


    override suspend fun addCategory(category: Category): Flow<DataState<Category>> = flow {
        val idFromFireStore = firestore.collection(CATEGORY_COLLECTION).document().id
        category.apply {
            categoryId = idFromFireStore
        }

        categoryDao.insertNewCategory(category)
        emit(DataState.OnData(category))
    }.flowOn(dispatcherProvider.io())

    override suspend fun getListCategory(): Flow<List<Category>> = categoryDao.getListCategory().flowOn(dispatcherProvider.io())
    override suspend fun updateCategory(category: Category): Flow<DataState<Category>> = flow<DataState<Category>> {
        categoryDao.updateCategory(category)
        emit(DataState.OnData(category))
    }.flowOn(dispatcherProvider.io())

    override suspend fun deleteCategory(category: Category): Flow<DataState<Category>> = flow<DataState<Category>> {
        categoryDao.deleteCategory(category)
        emit(DataState.OnData(category))
    }.flowOn(dispatcherProvider.io())
}