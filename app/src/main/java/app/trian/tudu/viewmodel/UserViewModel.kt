package app.trian.tudu.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.trian.tudu.common.getTheme
import app.trian.tudu.data.local.AppSetting
import app.trian.tudu.data.repository.design.UserRepository
import app.trian.tudu.domain.DataState
import app.trian.tudu.domain.ThemeData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
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

    private var _appSetting = MutableLiveData<AppSetting>()
    val appSetting get() = _appSetting
    private var _isDarkTheme = MutableLiveData<ThemeData>()
    val isDarkTheme get() = _isDarkTheme


    /**
     * user setting
     * */
    fun getCurrentSetting() = viewModelScope.launch {
        userRepository.getCurrentAppSetting().onEach {
            if(it != null) {
                _appSetting.postValue(it)
            }
        }
            .collect()
    }

    fun updateCurrentSetting(appSetting: AppSetting) = viewModelScope.launch {
        userRepository.updateCurrentSetting(appSetting)
            .onEach {
                _appSetting.postValue(it)
                _isDarkTheme.postValue(appSetting.theme.getTheme())
            }
            .collect()
    }

    /**
     * cek id user already logged in ?
     * we use firebase auth
     * */
    fun userAlreadyLogin(callback:suspend (isLoggedIn:Boolean)->Unit)=viewModelScope.launch {
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
                DataState.OnLoading -> {}
                is DataState.OnData -> {callback(true,"Sign In Success")}
                is DataState.OnFailure -> {callback(false,result.message)}
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
                        DataState.OnLoading -> {}
                        is DataState.OnData -> {
                            callback(true, "Sign in success")
                        }
                        is DataState.OnFailure -> {
                            callback(false, auth.message)
                        }
                    }
                }
            }else{
                callback(false,"User don't have credential")
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
                DataState.OnLoading -> {}
                is DataState.OnData -> {callback(true,"Register Success, Please check your email inbox to verify!")}
                is DataState.OnFailure -> {callback(false,result.message)}
            }
        }
    }


    fun changePassword(
        newPassword:String,
        callback: (success: Boolean, message: String) -> Unit
    ) = viewModelScope.launch {
        userRepository.changePassword(newPassword)
            .onEach {
                when(it){
                    DataState.OnLoading -> {}
                    is DataState.OnData -> callback(true,"Password has changed!")
                    is DataState.OnFailure -> callback(false,it.message)
                }
            }
            .collect()
    }


    fun resetPasswordEmail(
        email: String,
        callback: (success: Boolean, message: String) -> Unit
    ) = viewModelScope.launch {
        userRepository.resetPasswordEmail(email)
            .onEach {
                when(it){
                    is DataState.OnData -> callback(true,"Password has ben send to ${email}, Please check your email!")
                    is DataState.OnFailure -> callback(false,it.message)
                    DataState.OnLoading -> {}
                }
            }
            .collect()
    }

    fun registerNewToken() = viewModelScope.launch {
        userRepository.registerFCMTokenAndSubscribeTopic()
    }

    fun signOut(
        callback:()->Unit={}
    )=viewModelScope.launch{
        userRepository.signOut(callback = callback)
    }
}


