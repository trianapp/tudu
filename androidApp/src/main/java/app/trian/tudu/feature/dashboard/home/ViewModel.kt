package app.trian.tudu.feature.dashboard.home


import app.trian.tudu.R
import app.trian.tudu.base.BaseViewModelData
import app.trian.tudu.data.domain.category.GetListCategoryUseCase
import app.trian.tudu.data.domain.task.CreateTaskUseCase
import app.trian.tudu.data.domain.task.DeleteTaskUseCase
import app.trian.tudu.data.domain.task.GetListTaskUseCase
import app.trian.tudu.data.domain.task.SyncTaskToCloudUseCase
import app.trian.tudu.data.domain.task.UpdateTaskDoneUseCase
import app.trian.tudu.data.model.TaskCategoryModel
import app.trian.tudu.data.model.TaskModel
import app.trian.tudu.data.model.TodoModel
import app.trian.tudu.data.utils.Response
import app.trian.tudu.data.utils.ResponseWithProgress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getListTaskUseCase: GetListTaskUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val updateTaskDoneUseCase: UpdateTaskDoneUseCase,
    private val getListCategoryUseCase: GetListCategoryUseCase,
    private val syncTaskToCloudUseCase: SyncTaskToCloudUseCase
) : BaseViewModelData<HomeState, HomeDataState, HomeEvent>(HomeState(), HomeDataState()) {
    init {
        handleActions()
    }

    //region task
    private fun getListTask() = async {
        getListTaskUseCase().collect {
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

    private fun filterTask(categoryId: String) = asyncWithData {
        val filtered = if (categoryId.isEmpty() || categoryId == "all") {
            task
        } else {
            task.filter {
                categoryId in it.category.map { ct -> ct.categoryId }
            }
        }

        commitData {
            copy(
                filteredTask = filtered
            )
        }
    }

    private fun saveTask() = async {
        with(uiState.value) {
            createTaskUseCase(
                taskModel = TaskModel(
                    taskReminder = dueTime != null,
                    taskName = taskName,
                    taskDueDate = dueDate?.toString().orEmpty(),
                    taskDueTime = dueTime?.toString().orEmpty(),
                    taskDone = false
                ),
                taskCategoryModels = categories.map {
                    TaskCategoryModel(
                        categoryId = it.categoryId
                    )
                },
                todos = todos
            ).collect {
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
    }

    private fun deleteTask() = async {
        deleteTaskUseCase(uiState.value.taskId)
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
        updateTaskDoneUseCase(
            taskId = taskId,
            taskDone = isDone
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
        getListCategoryUseCase().collect {
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

    private fun createNewPlainTodo(todoName: String) = asyncWithState {
        if (todos.size <= 8) {
            commit {
                copy(
                    todos = todos.toMutableList().plus(
                        TodoModel(
                            todoId = UUID.randomUUID().toString(),
                            todoDone = false,
                            todoName = todoName,
                            createdAt = LocalDateTime.now().toString()
                        )
                    )
                )
            }
        } else {
            //only allow 8 sub task and unlimited on other page
        }
    }

    private fun updatePlainTodo(todoModel: TodoModel) = asyncWithState {
        val findIndex = todos
            .withIndex()
            .first { (_, value) -> value.todoId == todoModel.todoId }
            .index

        if (findIndex != -1) {
            val todo = todos.toMutableList()
            todo[findIndex] = todoModel
            commit {
                copy(
                    todos = todo
                )
            }
        }
    }

    private fun deletePlainTodo(todoId: String) = asyncWithState {
        val findIndex = todos
            .withIndex()
            .first { (_, value) -> value.todoId == todoId }
            .index

        if (findIndex != -1) {
            val todo = todos.toMutableList()
            todo.removeAt(findIndex)
            commit { copy(todos = todo) }
        }
    }

    //end region

    private fun syncTaskWithCloud() = async {
        syncTaskToCloudUseCase()
            .collect { res ->
                when (res) {
                    is ResponseWithProgress.Error -> {
                        showSnackbar(res.message)
                        commit { copy(isLoading = false) }
                    }

                    is ResponseWithProgress.Finish -> {
                        commit { copy(isLoading = false) }
                        getListTask()
                        getListCategory()
                    }

                    ResponseWithProgress.Loading -> commit { copy(isLoading = true) }
                    is ResponseWithProgress.Progress -> commit {
                        copy(
                            message = "Sync.. ${res.progress}%"
                        )
                    }
                }
            }
    }

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

            HomeEvent.SyncTask -> syncTaskWithCloud()
        }
    }

}
