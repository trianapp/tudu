package app.trian.tudu.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.trian.tudu.data.repository.design.UserRepository
import app.trian.tudu.domain.DataState
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
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

    fun logInWithEmailAndPassword(
        email:String,
        password:String,
        callback: (success: Boolean,message:String) -> Unit
    )=viewModelScope.launch {
        userRepository.loginBasic(email, password).collect{
            result->
            when(result){
                DataState.LOADING -> {}
                is DataState.onData -> {callback(true,"Sign In Success")}
                is DataState.onFailure -> {callback(false,result.message)}
            }
        }
    }
    
    fun logInWithGoogle(
        credential:Task<GoogleSignInAccount>?,
        callback: (success: Boolean, message: String) -> Unit
    )=viewModelScope.launch {
        try {
            if(credential != null) {
                val account = credential.await()
                userRepository.loginGoogle(account.idToken!!).collect { auth ->
                    when (auth) {
                        DataState.LOADING -> {}
                        is DataState.onData -> {
                            callback(true, "Sign in success")
                        }
                        is DataState.onFailure -> {
                            callback(false, auth.message)
                        }
                    }
                }
            }else{
                callback(false,"User don;t have credential")
            }
        }catch (e:Exception){
            callback(false,e.message ?: "Unknown")
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
        userRepository.signOut(callback = callback)
    }
}


