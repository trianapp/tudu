package app.trian.tudu.data.domain.task

import app.trian.tudu.data.model.TaskCategoryModel
import app.trian.tudu.data.model.TaskModel
import app.trian.tudu.data.model.TodoModel
import app.trian.tudu.data.utils.Response
import app.trian.tudu.sqldelight.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class CreateTaskUseCase @Inject constructor(
    private val db:Database
) {
    operator fun invoke(
        taskModel: TaskModel,
        todos: List<TodoModel>,
        taskCategoryModels: List<TaskCategoryModel>
    ): Flow<Response<TaskModel>> = flow {
        emit(Response.Loading)
        val taskId:String = UUID.randomUUID().toString()
        val currentDateString = LocalDateTime.now().toString()
        db.taskQueries.insertTask(
            taskId = taskId,
            taskDone = if (taskModel.taskDone) 1 else 0,
            taskDueDate = taskModel.taskDueDate,
            taskDueTime = taskModel.taskDueTime,
            taskName = taskModel.taskName,
            taskNote = taskModel.taskNote,
            taskReminder = if (taskModel.taskReminder) 1 else 0,
            createdAt = currentDateString,
            updatedAt = currentDateString,
        )
        db.transaction {
            todos.forEach { todo ->
                db.todoQueries.insertTodo(
                    todoName = todo.todoName,
                    todoId = todo.todoId,
                    todoDone = if (todo.todoDone) 1 else 0,
                    todoTaskId = taskId,
                    createdAt = todo.createdAt
                )
            }
            taskCategoryModels.forEach { group ->
                db.taskCategoryQueries.insertTaskCategory(
                    taskCategoryId = UUID.randomUUID().toString(),
                    categoryId = group.categoryId,
                    taskId = taskId
                )
            }
        }
        emit(Response.Result(taskModel))
    }.flowOn(Dispatchers.Default)
}