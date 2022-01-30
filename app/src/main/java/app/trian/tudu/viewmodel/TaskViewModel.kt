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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
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

    private var _listTodo = MutableLiveData<List<Todo>>()
    val listTodo get() = _listTodo

    private var _listCategory = MutableLiveData<List<Category>>()
    val listCategory get() = _listCategory




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
             _listTodo.value = result
         }
    }


    fun addNewTask(
        task:Task,
        todo:List<Todo>
    )=viewModelScope.launch {
        taskRepository.createNewTask(task,todo).collect {

        }

    }

    fun getTaskById(taskId:String)=viewModelScope.launch{
        taskRepository.getTaskById(taskId).collect {
            result->
            _detailTask.value = result

        }
        getListTodo(taskId)
    }

    fun addNewCategory(categoryName:String)=viewModelScope.launch {
        val category = Category(
            name = categoryName,
            created_at = 0,
            updated_at = 0
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
    fun doneTodo(todo: Todo)=viewModelScope.launch {
        todo.apply {
            done = true
        }
        taskRepository.updateTodo(todo).collect{}
    }
    fun deleteTodo(todo: Todo)=viewModelScope.launch {
        taskRepository.deleteTodo(todo).collect {  }
    }

}