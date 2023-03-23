package app.trian.tudu.feature.dashboard.home


import app.trian.tudu.R
import app.trian.tudu.base.BaseViewModelData
import app.trian.tudu.data.model.TaskCategoryModel
import app.trian.tudu.data.model.TaskModel
import app.trian.tudu.data.model.TodoModel
import app.trian.tudu.data.sdk.task.CategorySDK
import app.trian.tudu.data.sdk.task.TaskSDK
import app.trian.tudu.data.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskSDK: TaskSDK,
    private val categorySDK: CategorySDK
) : BaseViewModelData<HomeState, HomeDataState, HomeEvent>(HomeState(), HomeDataState()) {
    init {
        handleActions()
    }

    //region task
    private fun getListTask() = async {
        taskSDK.getLisTask().collect {
            when (it) {
                is Response.Error -> Unit
                Response.Loading -> Unit
                is Response.Result ->
                    commitData {
                        copy(
                            task = it.data,
                            filteredTask = it.data
                        )
                    }
            }
        }
    }

    private fun filterTask(categoryId: String) {
        val filtered = if (categoryId.isEmpty() || categoryId == "all") {
            uiDataState.value.task
        } else {
            uiDataState.value.task.filter {
                categoryId in it.category.map { it.categoryId }
            }
        }

        commitData {
            copy(
                filteredTask = filtered
            )
        }
    }

    private fun saveTask() = async {
        val taskId = UUID.randomUUID().toString()
        val currentDateString = LocalDate.now().toString()
        taskSDK.createNewTask(
            taskModel = TaskModel(
                taskId = taskId,
                taskReminder = uiState.value.hasDueTime,
                taskNote = "",
                taskName = uiState.value.taskName,
                taskDueDate = uiState.value.dueDate?.toString().orEmpty(),
                taskDueTime = uiState.value.dueTime?.toString().orEmpty(),
                taskDone = false,
                taskDoneAt = currentDateString,
                createdAt = currentDateString,
                updatedAt = currentDateString
            ),
            taskCategoryModels = uiState.value.categories.map {
                TaskCategoryModel(
                    taskId = taskId,
                    taskCategoryId = UUID.randomUUID().toString(),
                    categoryId = it.categoryId
                )
            },
            todos = uiState.value.todos
        )
            .collect {
                when (it) {
                    is Response.Error -> showSnackbar(it.message)
                    Response.Loading -> Unit
                    is Response.Result -> {
                        hideBottomSheet()
                        resetState()
                        showSnackbar(R.string.message_success_save_task)
                        getListTask()
                    }
                }
            }
    }

    private fun deleteTask() = async {
        taskSDK.deleteTask(uiState.value.taskId)
            .collect {
                when (it) {
                    is Response.Error -> Unit
                    Response.Loading -> Unit
                    is Response.Result -> {
                        getListTask()
                        commit {
                            copy(
                                showDialogDeleteTask = false,
                                taskId = ""
                            )
                        }

                    }
                }
            }
    }

    private fun doneTask(
        taskId: String,
        isDone: Boolean
    ) = async {
        taskSDK.updateTaskDone(
            taskId = taskId,
            isDone = isDone,
            doneAt = LocalDate.now().toString()
        )
            .collect {
                when (it) {
                    is Response.Error -> Unit
                    Response.Loading -> Unit
                    is Response.Result -> {
                        getListTask()
                    }
                }
            }
    }


    //end region

    //region category
    private fun getListCategory() = async {
        categorySDK.getListCategory().collect {
            when (it) {
                is Response.Error -> Unit
                Response.Loading -> Unit
                is Response.Result -> commitData {
                    copy(
                        categories = it.data
                    )
                }
            }
        }
    }

    //end region

    //region todo

    private fun createNewPlainTodo(todoName: String) = async {
        if (uiState.value.todos.size <= 8) {
            commit {
                copy(
                    todos = todos.toMutableList().plus(
                        TodoModel(
                            todoId = UUID.randomUUID().toString(),
                            todoDone = false,
                            todoName = todoName,
                            createdAt = LocalDate.now().toString()
                        )
                    )
                )
            }
        } else {
            //only allow 8 sub task and unlimited on other page
        }
    }

    private fun updatePlainTodo(todo: TodoModel) = async {
        val find = uiState
            .value
            .todos
            .withIndex()
            .first { (_, value) -> value.todoId == todo.todoId }
            .index

        if (find != -1) {
            val td = uiState.value.todos.toMutableList()
            td[find] = todo
            commit {
                copy(
                    todos = td
                )
            }
        }
    }

    private fun deletePlainTodo(todoId: String) = async {
        val find = uiState
            .value
            .todos
            .withIndex()
            .first { (_, value) -> value.todoId == todoId }
            .index

        if (find != -1) {
            val td = uiState.value.todos.toMutableList()
            td.removeAt(find)
            commit {
                copy(
                    todos = td
                )
            }
        }
    }

    //end region


    override fun handleActions() = onEvent {
        when (it) {
            is HomeEvent.AddPlainTodo -> createNewPlainTodo(it.todoName)
            is HomeEvent.UpdatePlainTodo -> updatePlainTodo(it.todo)
            is HomeEvent.DeletePlainTodo -> deletePlainTodo(it.todoId)
            HomeEvent.SubmitTask -> saveTask()
            HomeEvent.DeleteTask -> deleteTask()
            is HomeEvent.DoneTask -> doneTask(it.taskId, it.isDone)
            is HomeEvent.FilterTask -> filterTask(it.categoryId)
            HomeEvent.GetData -> {
                getListTask()
                getListCategory()
            }
        }
    }

}
