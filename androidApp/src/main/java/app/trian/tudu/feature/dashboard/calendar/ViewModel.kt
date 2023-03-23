package app.trian.tudu.feature.dashboard.calendar

import app.trian.tudu.R
import app.trian.tudu.base.BaseViewModelData
import app.trian.tudu.data.model.TaskCategoryModel
import app.trian.tudu.data.model.TaskModel
import app.trian.tudu.data.model.TodoModel
import app.trian.tudu.data.sdk.task.CategorySDK
import app.trian.tudu.data.sdk.task.TaskSDK
import app.trian.tudu.data.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val taskSDK: TaskSDK,
    private val categorySDK: CategorySDK
) : BaseViewModelData<CalendarState, CalendarDataState, CalendarEvent>(CalendarState(), CalendarDataState()) {
    init {
        handleActions()
    }

    //region task
    private fun getListTask() = async {
        val from = uiState.value.selectedDate
        val to = from.plusDays(1)
        taskSDK.getLisTaskByDate(from.toString(),to.toString())
            .catch {  }
            .collect{
                when(it){
                    is Response.Error -> showSnackbar(R.string.message_failed_fetch_data)
                    Response.Loading -> Unit
                    is Response.Result -> commitData { copy(task = it.data) }
                }
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
                        resetState()
                        showSnackbar(R.string.message_success_save_task)
                        hideBottomSheet()
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
            is CalendarEvent.AddPlainTodo -> createNewPlainTodo(it.todoName)
            is CalendarEvent.DeletePlainTodo -> deletePlainTodo(it.todoId)
            CalendarEvent.GetData -> {
                getListTask()
                getListCategory()
            }
            CalendarEvent.SubmitTask -> saveTask()
            is CalendarEvent.UpdatePlainTodo -> updatePlainTodo(it.todo)
            is CalendarEvent.GetTask -> {
                commit { copy(selectedDate = it.date) }
                getListTask()
            }

        }
    }

}