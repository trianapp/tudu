package app.trian.tudu.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.trian.tudu.data.repository.design.UserRepository
import app.trian.tudu.domain.DataState
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Database
 * author Trian Damai
 * created_at 28/01/22 - 10.02
 * site https://trian.app
 */
@HiltViewModel
class UserViewModel @Inject constructor():ViewModel() {
    @Inject  lateinit var userRepository: UserRepository

    private var _currentUser = MutableLiveData<FirebaseUser?>(null)
    val currentUser get() = _currentUser


    /**
     * cek id user already logged in ?
     * we user firebase auth
     * */
    fun userAlreadyLogin(callback:(isLoggedIn:Boolean)->Unit)=viewModelScope.launch {
        delay(1000)
        userRepository.checkIsUserLogin()
            .collect {
               callback(it)
            }
    }

    fun getCurrentUser()=viewModelScope.launch {
        userRepository.getCurrentUser().collect{
            result->
            _currentUser.value=result
        }
    }

    fun loggedInWithEmailAndPassword(
        email:String,
        password:String,
        callback: (success: Boolean,message:String) -> Unit
    )=viewModelScope.launch {
        userRepository.loginBasic(email, password).collect{
            result->
            when(result){
                DataState.LOADING -> {}
                is DataState.onData -> {callback(true,"Sukses")}
                is DataState.onFailure -> {callback(false,result.message)}
            }
        }
    }

    fun registerWithEmailAndPassword(
        username:String,
        email:String,
        password:String,
        callback: (success: Boolean,message:String) -> Unit
    )=viewModelScope.launch {
        userRepository.registerBasic(username,email, password).collect {
            result->
            when(result){
                DataState.LOADING -> {}
                is DataState.onData -> {callback(true,"sukses")}
                is DataState.onFailure -> {callback(false,result.message)}
            }
        }
    }

    fun signOut(
        callback:()->Unit={}
    )=viewModelScope.launch{
        userRepository.signOut {

        }
    }
}


