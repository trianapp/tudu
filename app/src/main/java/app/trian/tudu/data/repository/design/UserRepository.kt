package app.trian.tudu.data.repository.design

import app.trian.tudu.data.local.AppSetting
import app.trian.tudu.domain.DataState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun checkIsUserLogin():Flow<Boolean>

    suspend fun getCurrentUser():Flow<FirebaseUser?>

    suspend fun loginBasic(email:String,password:String):Flow<DataState<FirebaseUser>>
    suspend fun registerBasic(username:String,email:String,password:String):Flow<DataState<FirebaseUser>>

    suspend fun loginGoogle(idToken:String):Flow<DataState<FirebaseUser>>

    suspend fun registerFCMTokenAndSubscribeTopic()

    suspend fun changePassword(newPassword:String):Flow<DataState<Boolean>>

    suspend fun resetPasswordEmail(email: String):Flow<DataState<Boolean>>

    suspend fun getCurrentAppSetting(uid:String):Flow<AppSetting?>

    suspend fun signOut(callback:()->Unit)
}