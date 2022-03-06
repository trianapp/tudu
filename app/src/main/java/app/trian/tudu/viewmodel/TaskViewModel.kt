package app.trian.tudu.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.trian.tudu.common.*
import app.trian.tudu.data.local.Category
import app.trian.tudu.data.local.Task
import app.trian.tudu.data.local.Todo
import app.trian.tudu.data.repository.design.TaskRepository
import app.trian.tudu.data.repository.design.UserRepository
import app.trian.tudu.domain.ChartModelData
import app.trian.tudu.ui.theme.HexToJetpackColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import logcat.LogPriority
import logcat.logcat
import java.time.OffsetDateTime
import javax.inject.Inject

/**
 * TaskViewModel
 * author Trian Damai
 * created_at 29/01/22 - 13.05
 * site https://trian.app
 */

@HiltViewModel
class TaskViewModel @Inject constructor() : ViewModel() {
    @Inject lateinit var userRepository: UserRepository
    @Inject lateinit var taskRepository: TaskRepository

    private var _listTask = MutableLiveData<List<Task>>()
    val listTask get() = _listTask

    private var _detailTask = MutableLiveData<Task>()
    val detailTask get() = _detailTask

    private var _completeTodo = MutableLiveData<List<Todo>>()
    val completeTodo get() = _completeTodo

    private var _unCompleteTodo = MutableLiveData<List<Todo>>()
    val unCompleteTodo get() = _unCompleteTodo

    private var _listCategory = MutableLiveData<List<Category>>()
    val listCategory get() = _listCategory

    private var _category = MutableLiveData<Category>()
    val category get() = _category

    private var _allTaskCount = MutableLiveData<Int>(0)
    val allTaskCount get() = _allTaskCount

    private var _completedTaskCount = MutableLiveData<Int>(0)
    val completedTaskCount get() = _completedTaskCount

    private var _unCompleteTaskCount = MutableLiveData(0)
    val unCompleteTaskCount get() = _unCompleteTaskCount

    private var _chartCompleteTask = MutableLiveData<ChartModelData>()
    val chartCompleteTask get() = _chartCompleteTask

    private var _currentDate = MutableLiveData(getNowMillis())
    val currentDate get() = _currentDate

    private var _listTaskCalendar = MutableLiveData<List<Task>>(emptyList())
    val listTaskCalendar get() = _listTaskCalendar


    fun getListTask()=viewModelScope.launch {
        taskRepository.getListTask().onEach {
            result->
            _listTask.postValue(result)
        }.collect()
    }

    fun getListTaskByDate(date:OffsetDateTime)=viewModelScope.launch {
        taskRepository.getListTaskByDate(date)
            .onEach {
                _listTaskCalendar.postValue(it)
            }
            .collect()
    }

    fun deleteTask(task:Task)=viewModelScope.launch {
        taskRepository.deleteTask(task)
            .onEach {

            }
            .collect()
    }

    fun getListTaskByCategory(categoryId:String)=viewModelScope.launch {
        taskRepository.getListTaskByCategory(categoryId).onEach {
            result->
            _listTask.postValue(result)
        }.collect()
    }

    fun getListCategory()=viewModelScope.launch {
        taskRepository.getListCategory().onEach {
            result->
            _listCategory.value = result
        }.collect()
    }


    fun getCompleteTodo(taskId:String)=viewModelScope.launch {
         taskRepository.getListCompleteTodo(taskId).onEach {
             result->
             result.forEach {
                 logcat("yoo",LogPriority.ERROR) { it.toString() }
             }
             _completeTodo.value = result

         }.collect()
    }

    fun getUnCompleteTodo(taskId: String) = viewModelScope.launch {
        taskRepository.getListUnCompleteTodo(taskId).onEach{
            result->

            _unCompleteTodo.value = result
        }
            .collect()
    }

    fun calculateTaskCount()=viewModelScope.launch {
        taskRepository.getListTask().onEach{
            tasks->
            _allTaskCount.value = tasks.size
            _completedTaskCount.value = tasks.filter { it.done }.size
            _unCompleteTaskCount.value = tasks.filter { !it.done }.size
        }.collect()
    }

    fun getStatisticChart() = viewModelScope.launch {
        getStatisticChart(_currentDate.value ?: getNowMillis())

    }

    private fun getStatisticChart(date:OffsetDateTime) = viewModelScope.launch {
        taskRepository.getWeekCompleteCount(date).onEach {
            _chartCompleteTask.postValue(it)
        }
            .collect()
    }

    fun getStatistic(isNext:Boolean) = viewModelScope.launch {
        val date = if(isNext) (_currentDate.value ?: getNowMillis()).getNextWeek() else (_currentDate.value ?: getNowMillis()).getPreviousWeek()
        getStatisticChart(date)
        if(isNext){
            _currentDate.postValue(date)
        }else{
            _currentDate.postValue(date)
        }

    }

    fun addNewTask(
        task:Task,
        todo:List<Todo>
    )=viewModelScope.launch {
        taskRepository.createNewTask(task,todo).onEach{}.collect()

    }

    fun updateTask(
        task: Task
    )=viewModelScope.launch {
        taskRepository.updateTask(task).onEach{}.collect()
    }

    fun getTaskById(taskId:String)=viewModelScope.launch{
        taskRepository.getTaskById(taskId).onEach {
            result->
            _detailTask.value = result

        }.collect()
        getListCategory()
        getCompleteTodo(taskId)
    }

    fun sendBackupTask() = viewModelScope.launch {
        taskRepository.sendBackupTaskToCloud().onEach {  }.collect()
    }

    fun getBackupTaskFromCloud() = viewModelScope.launch {

        taskRepository.getBackupTaskFromCloud().onEach {  }.collect()
    }

    @SuppressLint("NewApi")
    fun addNewCategory(categoryName:String)=viewModelScope.launch {
        val category = Category(
            name = categoryName,
            created_at = OffsetDateTime.now(),
            updated_at = OffsetDateTime.now(),
            color = HexToJetpackColor.Blue
        )

        taskRepository.addCategory(category).onEach{}.collect()
    }
    fun updateCategory(category: Category) = viewModelScope.launch {
        taskRepository.updateCategory(category).onEach{}.collect()
    }

    fun deleteCategory(category: Category)=viewModelScope.launch {
        taskRepository.deleteCategory(category).onEach { }.collect()
    }

    @SuppressLint("NewApi")
    fun addNewTodo(todoName:String, taskId: String)=viewModelScope.launch {
        val todo = Todo(
            name = todoName,
            done = false,
            task_id = taskId,
            created_at = OffsetDateTime.now(),
            updated_at = OffsetDateTime.now()
        )

        taskRepository.addTodo(todo).onEach{}.collect()
    }

    fun updateTodo(todo: Todo)=viewModelScope.launch {
        taskRepository.updateTodo(todo).onEach{}.collect()
    }
    fun deleteTodo(todo: Todo)=viewModelScope.launch {
        taskRepository.deleteTodo(todo).onEach{}.collect()
    }

}