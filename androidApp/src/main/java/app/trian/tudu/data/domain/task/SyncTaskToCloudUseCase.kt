package app.trian.tudu.data.domain.task

import app.trian.tudu.data.model.TaskWithCategoryAndTodo
import app.trian.tudu.data.model.toModel
import app.trian.tudu.data.utils.ResponseWithProgress
import app.trian.tudu.sqldelight.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class SyncTaskToCloudUseCase @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val db: Database
) {

    operator fun invoke(): Flow<ResponseWithProgress<Boolean>> = flow {
        emit(ResponseWithProgress.Loading)
        try {
            emit(ResponseWithProgress.Progress(10))
            val user = auth.currentUser
            if (user == null) emit(ResponseWithProgress.Error("User not logged in!"))
            else {
                val userDataCollection = firestore
                    .collection("USER_DATA")
                    .document(user.uid)
                    .collection("TASK")

                val taskWithCategoryAndTodoList = db.transactionWithResult {
                    db.taskQueries
                        .getListTaskBySync(0)
                        .executeAsList()
                        .map { it.toModel() }
                        .map { task ->
                            val categories = db.taskCategoryQueries
                                .getAllTaskCategoryByTaskId(task.taskId)
                                .executeAsList()
                                .map {
                                    db.categoryQueries.getById(it.categoryId).executeAsOne()
                                        .toModel()
                                }

                            val todos = db.todoQueries.getListTodoByTask(task.taskId)
                                .executeAsList()
                                .map { it.toModel() }

                            TaskWithCategoryAndTodo(
                                task = task,
                                category = categories,
                                todos = todos
                            )
                        }
                }
                emit(ResponseWithProgress.Progress(20))
                firestore.runBatch { batch ->
                    taskWithCategoryAndTodoList
                        .forEach {
                            batch.set(
                                userDataCollection.document(it.task.taskId),
                                it,
                                SetOptions.merge()
                            )
                        }
                }
                emit(ResponseWithProgress.Progress(30))

                db.transaction {
                    val updatedAt = LocalDateTime.now().toString()
                    taskWithCategoryAndTodoList.forEach { task ->
                        db.taskQueries.updateTaskToIsUpload(
                            taskId = task.task.taskId,
                            isUploaded = 1,
                            updatedAt = updatedAt
                        )
                    }
                }
                emit(ResponseWithProgress.Progress(40))

                val listTask = userDataCollection.get()
                    .await()
                    .map {
                        it.toObject(TaskWithCategoryAndTodo::class.java)
                    }

                emit(ResponseWithProgress.Progress(60))
                db.transaction {
                    listTask.forEach { (taskModel, categories, todos) ->
                        val isExist =
                            db.taskQueries.getTaskById(taskModel.taskId).executeAsOneOrNull()
                        if (isExist == null) {
                            db.taskQueries.insertTask(
                                taskId = taskModel.taskId,
                                taskDone = if (taskModel.taskDone) 1 else 0,
                                taskDueDate = taskModel.taskDueDate,
                                taskDueTime = taskModel.taskDueTime,
                                taskName = taskModel.taskName,
                                taskNote = taskModel.taskNote,
                                taskReminder = if (taskModel.taskReminder) 1 else 0,
                                isUploaded = 1,
                                createdAt = taskModel.createdAt,
                                updatedAt = taskModel.updatedAt,
                            )


                            todos.forEach { todo ->
                                db.todoQueries.insertTodo(
                                    todoId = todo.todoId,
                                    todoName = todo.todoName,
                                    todoDone = if (todo.todoDone) 1 else 0,
                                    todoTaskId = taskModel.taskId,
                                    createdAt = taskModel.createdAt
                                )
                            }
                            categories.forEach { category ->
                                val isCategoryExist = db.categoryQueries
                                    .getById(category.categoryId)
                                    .executeAsOneOrNull()

                                if (isCategoryExist == null) {
                                    db.categoryQueries.insertCategory(
                                        categoryId = category.categoryId,
                                        categoryName = category.categoryName,
                                        createdAt = category.createdAt,
                                        updatedAt = category.updatedAt
                                    )
                                }
                                db.taskCategoryQueries.insertTaskCategory(
                                    categoryId = category.categoryId,
                                    taskId = taskModel.taskId,
                                    taskCategoryId = UUID.randomUUID().toString()
                                )

                            }
                        }
                    }
                }
                emit(ResponseWithProgress.Finish(true))

            }
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            emit(ResponseWithProgress.Error(e.message.orEmpty()))
        }

    }.flowOn(Dispatchers.IO)


}