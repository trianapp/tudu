package app.trian.tudu.feature.detailTask

import androidx.lifecycle.SavedStateHandle
import app.trian.tudu.base.BaseViewModelData
import app.trian.tudu.data.model.CategoryModel
import app.trian.tudu.data.model.TaskCategoryModel
import app.trian.tudu.data.sdk.task.CategorySDK
import app.trian.tudu.data.sdk.task.TaskSDK
import app.trian.tudu.data.sdk.task.TodoSDK
import app.trian.tudu.data.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DetailTaskViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val taskSDK: TaskSDK,
    private val todoSDK: TodoSDK,
    private val categorySDK: CategorySDK
) : BaseViewModelData<DetailTaskState, DetailTaskDataState, DetailTaskEvent>(DetailTaskState(), DetailTaskDataState()) {
    init {
        handleActions()
        getDetailTask()
    }

    private fun getDetailTask(){
        getDetailTask(getTaskId())
        getTaskCategories(getTaskId())
        getTaskTodos(getTaskId())
        getListCategory()
    }
    private fun getTaskId() = savedStateHandle.get<String>(DetailTask.keyArgs).orEmpty()

    private fun checkIsEditBeforeNavigate() = async {
        if (
            uiState.value.isUpdateTodo ||
            uiState.value.isUpdateCategory ||
            uiState.value.isUpdateTask
        ) {
            commit { copy(showDialogBackConfirmation = true) }
        } else {
            navigateUp()
        }
    }

    private fun getListCategory() = async {
        categorySDK.getListCategory().collect {
            when (it) {
                is Response.Error -> Unit
                Response.Loading -> Unit
                is Response.Result -> {
                    commitData {
                        copy(
                            allCategories = it.data
                        )
                    }
                }
            }
        }
    }

    private fun getDetailTask(taskId: String) = async {
        taskSDK.getDetailTask(taskId)
            .catch {
                showSnackbar("Failed to load data")
            }
            .collect {
                when (it) {
                    is Response.Error -> showSnackbar(it.message)
                    Response.Loading -> Unit
                    is Response.Result -> commit {
                        copy(
                            taskId = it.data.taskId,
                            taskName = it.data.taskName,
                            taskNote = it.data.taskNote,
                            taskDueDate = LocalDate.parse(it.data.taskDueDate.ifEmpty { LocalDate.now().toString() }),
                            taskReminder = it.data.taskReminder,
                            taskDueTime = LocalTime.parse(it.data.taskDueTime.ifEmpty { LocalTime.now().toString() })
                        )
                    }

                }
            }
    }

    private fun changeTaskName(taskName: String) = async {
        if (!uiState.value.isUpdateTask) {
            commit {
                copy(
                    isUpdateTask = true,
                    taskName = taskName
                )
            }
        } else {
            commit {
                copy(
                    taskName = taskName
                )
            }
        }
        updateTaskName(taskName = taskName)
    }

    private fun updateTaskName(taskName: String) = async {
        commit { copy(taskName = taskName) }
        taskSDK.updateTaskName(
            taskId = getTaskId(),
            taskName = taskName,
            updatedAt = LocalDate.now().toString()
        )
            .catch { }
            .collect {}
    }

    private fun updateTaskDueDate(taskDueDate: LocalDate) = async {
        commit {
            copy(
                taskDueDate = taskDueDate,
                showDialogPickDate = false
            )
        }
        taskSDK.updateTaskDueDate(
            taskId = getTaskId(),
            taskDueDate = taskDueDate.toString(),
            updatedAt = LocalDate.now().toString()
        )
            .catch { }
            .collect {}
    }

    private fun updateTaskDueTime(taskDueTime: LocalTime) = async {
        commit {
            copy(
                taskDueTime = taskDueTime,
                showDialogPickTime = false
            )
        }
        taskSDK.updateTaskDueTime(
            taskId = getTaskId(),
            taskDueTime = taskDueTime.toString(),
            updatedAt = LocalDate.now().toString()
        )
            .catch { }
            .collect {}
    }

    private fun updateTaskReminder(taskReminder: Boolean) = async {
        commit { copy(taskReminder = taskReminder) }
        taskSDK.updateTaskReminder(
            taskId = getTaskId(),
            taskReminder = taskReminder,
            updatedAt = LocalDate.now().toString()
        )
            .catch { }
            .collect {}
    }

    private fun updateTaskDone(taskDone: Boolean) = async {
        commit { copy(taskDone = taskDone) }
        taskSDK.updateTaskDone(
            taskId = getTaskId(),
            isDone = taskDone,
            doneAt = LocalDate.now().toString()
        )
            .catch { }
            .collect {}
    }

    private fun updateTaskCategory(categories: List<CategoryModel>) = async {
        val taskId = getTaskId()
        val oldCategory = uiDataState.value.categories
        commitData {
            copy(
                categories = categories,
            )
        }
        commit { copy(showDialogPickCategory = false) }
        taskSDK.updateTaskCategory(
            getTaskId(),
            oldCategories = oldCategory,
            newCategories = categories.map {
                TaskCategoryModel(
                    taskId = taskId,
                    categoryId = it.categoryId,
                    taskCategoryId = UUID.randomUUID().toString()
                )
            }
        ).collect{}
    }

    private fun deleteTask() = async {
        taskSDK.deleteTask(getTaskId())
            .collect {
                when (it) {
                    is Response.Error -> Unit
                    Response.Loading -> Unit
                    is Response.Result -> {
                        onCleared()
                        navigateUp()
                    }
                }
            }
    }

    private fun getTaskCategories(taskId: String) = async {
        categorySDK.getCategoryByTask(taskId)
            .catch { }
            .collect {
                when (it) {
                    is Response.Error -> Unit
                    Response.Loading -> Unit
                    is Response.Result -> commitData {
                        copy(categories = it.data)
                    }
                }
            }

    }

    private fun getTaskTodos(taskId: String) = async {
        todoSDK.getListTodoByTask(taskId)
            .catch { }
            .collect {
                when (it) {
                    is Response.Error -> Unit
                    Response.Loading -> Unit
                    is Response.Result -> commitData {
                        copy(
                            todos = it.data
                        )
                    }
                }
            }
    }

    private fun deleteTaskTodo(todoId: String) = async {
        todoSDK.deleteTodo(todoId)
            .catch { }
            .collect {}
    }

    private fun createTaskTodo() = async {
        todoSDK.createNewTodo(
            taskId = getTaskId(),
            todoId = UUID.randomUUID().toString(),
            todoName = "",
            createdAt = LocalDate.now().toString()
        )
            .collect {
                when (it) {
                    is Response.Error -> Unit
                    Response.Loading -> Unit
                    is Response.Result -> {
                        getTaskTodos(getTaskId())
                    }
                }
            }
    }

    private fun updateTempTodoName(todoId: String, todoName: String) = async {
        val find = uiDataState
            .value
            .todos
            .withIndex()
            .first { (_, value) -> value.todoId == todoId }
            .index

        if (find != -1) {
            val td = uiDataState.value.todos.toMutableList()
            td[find] = td[find].copy(todoName = todoName)
            commitData {
                copy(
                    todos = td
                )
            }
        }
        updateTodoName(
            todoId = todoId,
            todoName = todoName
        )
    }

    private fun updateTodoName(todoId: String, todoName: String) = async {
        todoSDK.updateTodoName(
            todoName = todoName,
            todoId = todoId,
        )
            .catch { }
            .collect {}
    }

    private fun updateTempTodoDone(todoId: String, todoDone: Boolean) = async {
        val find = uiDataState
            .value
            .todos
            .withIndex()
            .first { (_, value) -> value.todoId == todoId }
            .index

        if (find != -1) {
            val td = uiDataState.value.todos.toMutableList()
            td[find] = td[find].copy(todoDone = todoDone)
            commitData {
                copy(
                    todos = td
                )
            }
        }
        updateTodoDone(
            todoId = todoId,
            todoDone = todoDone
        )
    }

    private fun updateTodoDone(todoId: String, todoDone: Boolean) = async {
        todoSDK.updateTodoDone(
            todoDone = todoDone,
            todoId = todoId,
        )
            .catch { }
            .collect {}
    }

    private fun deleteTempTodo(todoId: String) = async {
        val find = uiDataState
            .value
            .todos
            .withIndex()
            .first { (_, value) -> value.todoId == todoId }
            .index

        if (find != -1) {
            val td = uiDataState.value.todos.toMutableList()
            td.removeAt(find)
            commitData {
                copy(
                    todos = td
                )
            }
        }
        deleteTaskTodo(todoId)
    }

    private fun resetFormState() {
        showSnackbar("Tsk has been saved!")
        commit {
            copy(
                isUpdateTask = false,
                isUpdateCategory = false,
                isUpdateTodo = false,
                showDialogPickTime = false,
                showDialogPickDate = false,
                showDialogPickCategory = false,
                showDialogBackConfirmation = false,
                showDialogDeleteConfirmation = false
            )
        }
    }

    override fun handleActions() = onEvent {
        when (it) {
            DetailTaskEvent.GetDetailTask -> getDetailTask()
            DetailTaskEvent.CheckBackPressed -> checkIsEditBeforeNavigate()
            is DetailTaskEvent.UpdateTaskName -> changeTaskName(it.taskName)
            is DetailTaskEvent.UpdateTaskCategory -> updateTaskCategory(it.categories)
            is DetailTaskEvent.UpdateTaskDueDate -> updateTaskDueDate(it.dueDate)
            is DetailTaskEvent.UpdateTaskDueTime -> updateTaskDueTime(it.dueTime)
            is DetailTaskEvent.UpdateTaskReminder -> updateTaskReminder(it.reminder)
            is DetailTaskEvent.UpdateTaskDone -> updateTaskDone(it.taskDone)
            DetailTaskEvent.DeleteTask -> deleteTask()
            DetailTaskEvent.SubmitTask -> resetFormState()
            is DetailTaskEvent.DeleteTodo -> deleteTempTodo(it.todoId)
            DetailTaskEvent.CreateTodo -> createTaskTodo()
            is DetailTaskEvent.UpdateTodoDone -> updateTempTodoDone(
                todoId = it.todoId,
                todoDone = it.isDone
            )

            is DetailTaskEvent.UpdateTodoName -> updateTempTodoName(
                todoId = it.todoId,
                todoName = it.todoName
            )
        }
    }

}