package app.trian.tudu.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.trian.tudu.data.local.Category
import app.trian.tudu.data.local.Task
import app.trian.tudu.data.repository.design.TaskRepository
import app.trian.tudu.data.repository.design.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import logcat.logcat
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


    fun getListTask()=viewModelScope.launch {
        taskRepository.getListTask().collect {
            result->
            _listTask.value = result
        }
    }

    fun addNewTask()=viewModelScope.launch {

        val task = Task(
            taskId="iniasna",
            uid="iniuid",
            name="task pertama",
            deadline=0,
            done=false,
            done_at=0,
            note="ini",
            category_id="b",
            created_at=0,
            updated_at=1
        )

        taskRepository.createNewTask(task).collect {

        }

    }

}