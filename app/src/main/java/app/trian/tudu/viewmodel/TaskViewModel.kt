package app.trian.tudu.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.trian.tudu.data.local.Category
import app.trian.tudu.data.local.Task
import app.trian.tudu.data.local.Todo
import app.trian.tudu.data.repository.design.TaskRepository
import app.trian.tudu.data.repository.design.UserRepository
import app.trian.tudu.domain.DataState
import app.trian.tudu.ui.theme.HexToJetpackColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    private var _detailTask = MutableLiveData<DataState<Task>>()
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

    private var _unCompleteTaskCount = MutableLiveData<Int>(0)
    val unCompleteTaskCount get() = _unCompleteTaskCount




    fun getListTask()=viewModelScope.launch {
        taskRepository.getListTask().collect {
            result->
            _listTask.value = result
        }
    }


    fun getListTaskByCategory(categoryId:String)=viewModelScope.launch {
        taskRepository.getListTaskByCategory(categoryId).collect {
            result->
            _listTask.value = result
        }
    }

    fun getListCategory()=viewModelScope.launch {
        taskRepository.getListCategory().collect {
            result->
            _listCategory.value = result
        }
    }


    fun getListTodo(taskId:String)=viewModelScope.launch {
         taskRepository.getListTodo(taskId).collect {
             result->
             _completeTodo.value = result.filter { it.done }
             _unCompleteTodo.value = result.filter { !it.done }
         }
    }

    fun calculateTaskCount()=viewModelScope.launch {
        taskRepository.getListTask().collect{
            tasks->
            _allTaskCount.value = tasks.size
            _completedTaskCount.value = tasks.filter { it.done }.size
            _unCompleteTaskCount.value = tasks.filter { !it.done }.size
        }
    }

    fun addNewTask(
        task:Task,
        todo:List<Todo>
    )=viewModelScope.launch {
        taskRepository.createNewTask(task,todo).collect {

        }

    }

    fun updateTask(
        task: Task
    )=viewModelScope.launch {
        taskRepository.updateTask(task).collect {

        }
    }

    fun getTaskById(taskId:String)=viewModelScope.launch{
        taskRepository.getTaskById(taskId).collect {
            result->
            _detailTask.value = result

        }
        getListCategory()
        getListTodo(taskId)
    }

    fun addNewCategory(categoryName:String)=viewModelScope.launch {
        val category = Category(
            name = categoryName,
            created_at = 0,
            updated_at = 0,
            color = HexToJetpackColor.Blue
        )
        taskRepository.addCategory(category).collect {

        }
    }

    fun addNewTodo(todoName:String,taskId: String)=viewModelScope.launch {
        val todo = Todo(
            name = todoName,
            done = false,
            task_id = taskId,
            created_at = 0,
            updated_at = 0
        )

        taskRepository.addTodo(todo).collect {  }
    }

    fun updateTodo(todo: Todo)=viewModelScope.launch {
        taskRepository.updateTodo(todo).collect {  }
    }
    fun deleteTodo(todo: Todo)=viewModelScope.launch {
        taskRepository.deleteTodo(todo).collect {  }
    }

}