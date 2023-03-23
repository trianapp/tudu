package app.trian.tudu.data.sdk.task


import app.trian.tudu.data.DriverFactory
import app.trian.tudu.data.createDatabase
import app.trian.tudu.data.model.CategoryModel
import app.trian.tudu.data.model.ChartModelData
import app.trian.tudu.data.model.CountTask
import app.trian.tudu.data.model.TaskCategoryModel
import app.trian.tudu.data.model.TaskModel
import app.trian.tudu.data.model.TaskWithCategory
import app.trian.tudu.data.model.TodoModel
import app.trian.tudu.data.model.toModel
import app.trian.tudu.data.utils.Response
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TaskSDK(
    driverFactory: DriverFactory,
) {
    private val db = createDatabase(driverFactory)

    //region task
    suspend fun getLisTask(
    ): Flow<Response<List<TaskWithCategory>>> = flow {
        emit(Response.Loading)
        val result = db.transactionWithResult {
            val tasks = db.taskQueries
                .getListTask()
                .executeAsList()
                .map { it.toModel() }

            val categories = db.categoryQueries
                .getListCategory()
                .executeAsList()
                .map { it.toModel() }

            val getListCategoryTask = db.taskCategoryQueries
                .getAllTaskCategory()
                .executeAsList()
                .map { it.toModel() }

            tasks.map { taskModel ->
                val findGroup = getListCategoryTask
                    .filter { group -> group.taskId == taskModel.taskId }
                    .map { group ->
                        categories
                            .filter { category -> category.categoryId == group.categoryId }
                    }
                    .flatten()

                TaskWithCategory(
                    taskModel,
                    findGroup
                )
            }
        }
        emit(Response.Result(result))
    }.flowOn(Dispatchers.Default)

    suspend fun getLisTaskByDate(
        from: String,
        to: String
    ): Flow<Response<List<TaskWithCategory>>> = flow {
        emit(Response.Loading)
        val result = db.transactionWithResult {
            val tasks = db.taskQueries
                .getListByDate(
                    from,
                    to
                )
                .executeAsList()
                .map { it.toModel() }

            val categories = db.categoryQueries
                .getListCategory()
                .executeAsList()
                .map { it.toModel() }

            val getListCategoryTask = db.taskCategoryQueries
                .getAllTaskCategory()
                .executeAsList()
                .map { it.toModel() }

            tasks.map { taskModel ->
                val findGroup = getListCategoryTask
                    .filter { group -> group.taskId == taskModel.taskId }
                    .map { group ->
                        categories
                            .filter { category -> category.categoryId == group.categoryId }
                    }
                    .flatten()

                TaskWithCategory(
                    taskModel,
                    findGroup
                )
            }
        }
        emit(Response.Result(result))
    }.flowOn(Dispatchers.Default)

    suspend fun getStatisticTask(): Flow<Response<CountTask>> = flow {
        emit(Response.Loading)
        val allTask = db.taskQueries.getListTask().executeAsList().map { it.toModel() }
        val allTaskCount = allTask.size
        val completed = allTask.filter { it.taskDone }.size
        val pending = allTask.filter { !it.taskDone }.size

        emit(
            Response.Result(
                CountTask(
                    totalTask = allTaskCount,
                    completedTask = completed,
                    pendingTask = pending
                )
            )
        )
    }.flowOn(Dispatchers.IO)

    suspend fun getChartTask(from: LocalDate): Flow<Response<ChartModelData>> = flow {
        val result = db.transactionWithResult {
            val startWeek = from.minusDays(7)
            var nextDay = from.plusDays(1)
            var lastDay = nextDay.minusDays(1)
            var currentMaxAxis = 0f

            val totalDays = listOf(6, 5, 4, 3, 2, 1, 0).reversed()
            var entries = listOf<BarEntry>()
            var labels = listOf<String>()
            totalDays.forEachIndexed { _, i ->
                val dataCount =
                    db.taskQueries
                        .getListByDate(nextDay.toString(), lastDay.toString())
                        .executeAsList()
                        .filter { it.taskDone?.toInt() == 1 }
                        .size
                if (currentMaxAxis < dataCount) {
                    currentMaxAxis = (dataCount + 2).toFloat()
                }

                entries = entries + BarEntry(
                    i.toFloat(),
                    dataCount.toFloat()
                )
                nextDay = lastDay
                lastDay = nextDay.minusDays(1)

                labels = labels + nextDay.format(DateTimeFormatter.ofPattern("dd/MM"))
            }
            ChartModelData(
                items = entries,
                labels = labels,
                from = startWeek,
                to = from,
                max = currentMaxAxis,
                min = 0f
            )
        }
        emit(Response.Result(result))
    }.flowOn(Dispatchers.IO)

    suspend fun getDetailTask(taskId: String): Flow<Response<TaskModel>> = flow {
        emit(Response.Loading)
        val result = db.taskQueries
            .getTaskById(taskId)
            .executeAsOneOrNull()
            ?: throw NullPointerException("Not found")
        emit(Response.Result(result.toModel()))
    }.flowOn(Dispatchers.Default)

    suspend fun createNewTask(
        taskModel: TaskModel,
        todos: List<TodoModel>,
        taskCategoryModels: List<TaskCategoryModel>
    ): Flow<Response<TaskModel>> = flow {
        emit(Response.Loading)
        db.taskQueries.insertTask(
            taskId = taskModel.taskId,
            taskDone = if (taskModel.taskDone) 1 else 0,
            taskDueDate = taskModel.taskDueDate,
            taskDueTime = taskModel.taskDueTime,
            taskName = taskModel.taskName,
            taskNote = taskModel.taskNote,
            taskReminder = if (taskModel.taskReminder) 1 else 0,
            createdAt = taskModel.createdAt,
            updatedAt = taskModel.updatedAt
        )
        db.transaction {
            todos.forEach { todo ->
                db.todoQueries.insertTodo(
                    todoName = todo.todoName,
                    todoId = todo.todoId,
                    todoDone = if (todo.todoDone) 1 else 0,
                    todoTaskId = taskModel.taskId,
                    createdAt = todo.createdAt
                )
            }
            taskCategoryModels.forEach { group ->
                db.taskCategoryQueries.insertTaskCategory(
                    taskCategoryId = group.taskCategoryId,
                    categoryId = group.categoryId,
                    taskId = taskModel.taskId
                )
            }
        }
        emit(Response.Result(taskModel))
    }.flowOn(Dispatchers.Default)

    suspend fun deleteTask(taskId: String): Flow<Response<Boolean>> = flow<Response<Boolean>> {
        emit(Response.Loading)
        db.transaction {
            db.taskQueries.deleteTask(taskId)
            db.taskCategoryQueries.deleteTaskCategoryByTask(taskId)
            db.todoQueries.deleteTodoByTask(taskId)
        }
        emit(Response.Result(true))
    }.flowOn(Dispatchers.Default)

    suspend fun updateTaskName(
        taskId: String,
        taskName: String,
        updatedAt: String
    ): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        db.taskQueries.updateTaskName(
            taskId = taskId,
            taskName = taskName,
            updatedAt = updatedAt
        )
        emit(Response.Result(true))
    }.flowOn(Dispatchers.Default)

    suspend fun updateTaskDone(
        taskId: String,
        isDone: Boolean,
        doneAt: String
    ): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        db.taskQueries.updateTaskToDone(
            taskDone = if (isDone) 1 else 0,
            taskDoneAt = doneAt,
            taskId = taskId,
            updatedAt = doneAt
        )
        emit(Response.Result(true))
    }.flowOn(Dispatchers.Default)

    suspend fun updateTaskNote(
        taskId: String,
        taskNote: String,
        updatedAt: String
    ): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        db.taskQueries.updateTaskNote(
            taskId = taskId,
            taskNote = taskNote,
            updatedAt = updatedAt
        )
        emit(Response.Result(true))
    }.flowOn(Dispatchers.Default)


    suspend fun updateTaskDueDate(
        taskId: String,
        taskDueDate: String,
        updatedAt: String
    ): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        db.taskQueries.updateTaskDueDate(
            taskId = taskId,
            taskDueDate = taskDueDate,
            updatedAt = updatedAt
        )
        emit(Response.Result(true))
    }.flowOn(Dispatchers.Default)

    suspend fun updateTaskDueTime(
        taskId: String,
        taskDueTime: String,
        updatedAt: String
    ): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        db.taskQueries.updateTaskDueTime(
            taskId = taskId,
            taskDueTime = taskDueTime,
            updatedAt = updatedAt
        )
        emit(Response.Result(true))
    }.flowOn(Dispatchers.Default)

    suspend fun updateTaskReminder(
        taskId: String,
        taskReminder: Boolean,
        updatedAt: String
    ): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        db.taskQueries.updateTaskReminder(
            taskId = taskId,
            taskReminder = if (taskReminder) 1 else 0,
            updatedAt = updatedAt
        )
        emit(Response.Result(true))
    }.flowOn(Dispatchers.Default)

    suspend fun updateTaskCategory(
        taskId: String,
        oldCategories: List<CategoryModel>,
        newCategories: List<TaskCategoryModel>
    ): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        db.transaction {
            oldCategories.forEach {
                db.taskCategoryQueries
                    .deletTaskCategoryByTaskAndCategory(
                        taskId = taskId,
                        categoryId = it.categoryId
                    )
            }
            newCategories.forEach {
                db.taskCategoryQueries.insertTaskCategory(
                    taskCategoryId = it.taskCategoryId,
                    taskId = it.taskId,
                    categoryId = it.categoryId
                )
            }
        }
        emit(Response.Result(true))
    }.flowOn(Dispatchers.Default)
    //end region

}
