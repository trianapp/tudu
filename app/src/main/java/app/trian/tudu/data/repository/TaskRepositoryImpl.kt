package app.trian.tudu.data.repository

import android.annotation.SuppressLint
import app.trian.tudu.common.*
import app.trian.tudu.data.local.*
import app.trian.tudu.data.local.dao.CategoryDao
import app.trian.tudu.data.local.dao.TaskDao
import app.trian.tudu.data.local.dao.TodoDao
import app.trian.tudu.data.repository.design.TaskRepository
import app.trian.tudu.domain.*
import com.github.mikephil.charting.data.BarEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import java.time.OffsetDateTime

class TaskRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val taskDao: TaskDao,
    private val todoDao: TodoDao,
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

    @SuppressLint("NewApi")
    override suspend fun getListTaskByDate(date: OffsetDateTime): Flow<List<Task>> {
        val current = date
        val previous = current.minusDays(1)
       val data = taskDao.getListTaskByDate(previous,current)
       return data.flowOn(dispatcherProvider.io())
    }


    override suspend fun getListTaskByCategory(categoryId: String): Flow<List<Task>> =taskDao.getListTaskByCategory(categoryId).flowOn(dispatcherProvider.io())

    override suspend fun getTaskById(taskId: String): Flow<Task?> = flow {
        val task = taskDao.getTaskById(taskId)
            emit(task)

    }.flowOn(dispatcherProvider.io())

    override suspend fun getWeekCompleteCount(date:OffsetDateTime): Flow<ChartModelData> =flow {

        val startWeek = date.getPreviousWeek()

        var currentTo = date.getNextDate()
        var currentFrom = currentTo.getPreviousDate()
        var currentMax = 0f

        val dayCount = listOf(6,5,4,3,2,1,0).reversed()
        var listEntry = listOf<BarEntry>()
        var listLabel = listOf<String>()
        dayCount.forEachIndexed { _, i ->
            val dataCount = taskDao.getCountCompleteTask(currentFrom,currentTo)

            if(currentMax < dataCount){
                currentMax = (dataCount + 2).toFloat()
            }

            listEntry = listEntry + BarEntry(
                i.toFloat(),
                dataCount.toFloat()
            )

            currentTo = currentFrom
            currentFrom = currentFrom.getPreviousDate()

            listLabel = listLabel + currentTo.formatDate("dd/MM")

        }
        emit(
            ChartModelData(
                items = listEntry,
                labels = listLabel.reversed(),
                dateFrom = startWeek,
                dateTo = date,
                max = currentMax,
                min = 0f
            )
        )
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

    override suspend fun deleteTask(task: Task): Flow<Task> =flow<Task> {
        taskDao.deleteTask(task)
        emit(task)
    }.flowOn(dispatcherProvider.io())

    override suspend fun getBackupTaskFromCloud(): Flow<DataState<List<Task>>> = flow {
        emit(DataState.OnLoading)
        val currentUser = firebaseAuth.currentUser
        if(currentUser == null) {
            emit(DataState.OnFailure("Login first!"))
        }else{
            try {
               val result = firestore.collection("TASK")
                    .document(currentUser.uid)
                    .get().await().toObject(TaskModel::class.java)!!

                taskDao.insertBatchTask(result.task)
                emit(DataState.OnFailure("sas"))
            }catch (e:Exception){
                emit(DataState.OnFailure(e.message ?: "Error retrieve data"))
            }
        }

    }.flowOn(dispatcherProvider.io())

    override suspend fun sendBackupTaskToCloud(): Flow<DataState<List<Task>>> = flow<DataState<List<Task>>> {
        emit(DataState.OnLoading)
        val currentUser = firebaseAuth.currentUser
        if(currentUser == null){
            emit(DataState.OnFailure("Login First!"))
        }else{
            try {
                val listTask = taskDao.getListTaskNoFlow()
                val taskModel = TaskModel(
                    task = listTask,
                    updated_at = 0
                )
                firestore.collection("TASK")
                    .document(currentUser.uid)
                    .set(taskModel.toHashMap(), SetOptions.merge())
            }catch (e:Exception){
                emit(DataState.OnFailure(e.message ?: "Error uploading data"))
            }
        }
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