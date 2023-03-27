package app.trian.tudu.feature.dashboard.calendar

import app.trian.tudu.R
import app.trian.tudu.base.BaseViewModelData
import app.trian.tudu.data.domain.category.GetListCategoryUseCase
import app.trian.tudu.data.domain.task.CreateTaskUseCase
import app.trian.tudu.data.domain.task.GetListTaskByDateUseCase
import app.trian.tudu.data.model.TaskCategoryModel
import app.trian.tudu.data.model.TaskModel
import app.trian.tudu.data.model.TodoModel
import app.trian.tudu.data.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getListTaskByDateUseCase: GetListTaskByDateUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val getListCategoryUseCase: GetListCategoryUseCase
) : BaseViewModelData<CalendarState, CalendarDataState, CalendarEvent>(
    CalendarState(),
    CalendarDataState()
) {
    init {
        handleActions()
    }

    //region task
    private fun getListTask() = asyncWithState {
        val from = selectedDate
        getListTaskByDateUseCase(from.toString(), from.toString()).collect {
            when (it) {
                is Response.Error -> showSnackbar(R.string.message_failed_fetch_data)
                Response.Loading -> Unit
                is Response.Result -> commitData { copy(task = it.data) }
            }
        }
    }

    private fun saveTask() = asyncWithState {
        createTaskUseCase(
            taskModel = TaskModel(
                taskReminder = hasDueTime,
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
            commit { copy(todos = todo) }
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