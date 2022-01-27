package app.trian.tudu.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.trian.tudu.data.repository.design.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
):ViewModel() {
    private val _counter = MutableLiveData<Int>(0)
    val counter get() = _counter

    fun increment(){
        _counter.value = (_counter.value?.plus(1))
    }
}