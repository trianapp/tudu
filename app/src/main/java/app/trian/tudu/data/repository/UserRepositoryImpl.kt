package app.trian.tudu.data.repository

import app.trian.tudu.common.DispatcherProvider
import app.trian.tudu.common.getNowMillis
import app.trian.tudu.data.local.AppSetting
import app.trian.tudu.data.local.dao.AppSettingDao
import app.trian.tudu.data.local.dao.TaskDao
import app.trian.tudu.data.local.dao.TodoDao
import app.trian.tudu.data.repository.design.UserRepository
import app.trian.tudu.domain.DataState
import app.trian.tudu.domain.UserToken
import app.trian.tudu.domain.toHashMap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.messaging.FirebaseMessaging

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val firebaseMessaging: FirebaseMessaging,
    private val taskDao: TaskDao,
    private val todoDao: TodoDao,
    private val appSettingDao: AppSettingDao
):UserRepository {
    override suspend fun checkIsUserLogin(): Flow<Boolean> = flow {
        val currentUser = firebaseAuth.currentUser
        if(currentUser == null) {
            emit(false)
        }else{
            emit(true)
        }
    }.flowOn(dispatcherProvider.io())

    override suspend fun getCurrentUser(): Flow<FirebaseUser?> = flow{
        val user = firebaseAuth.currentUser
        emit(user)
    }

    override suspend fun loginBasic(email: String, password: String): Flow<DataState<FirebaseUser>> = flow {
        emit(DataState.OnLoading)
        try {
            val authenticate = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = authenticate.user
            if(user == null){
                emit(DataState.OnFailure("Login failed!"))
                firebaseAuth.signOut()
            }else {
                if (user.isEmailVerified) {
                    emit(DataState.OnData(authenticate.user!!))
                }else{
                    emit(DataState.OnFailure("Email not verify, PLease check your email inbox!"))
                    firebaseAuth.signOut()
                }
            }
        }catch (e:Exception){
            emit(DataState.OnFailure("authentication failed because ${e.message}"))
        }
    }.flowOn(dispatcherProvider.io())

    override suspend fun registerBasic(username:String,email:String,password:String): Flow<DataState<FirebaseUser>> =flow {
        emit(DataState.OnLoading)
        try {
            val authenticate = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
            //
            val user = firebaseAuth.currentUser
            val profileChange = userProfileChangeRequest {
                displayName = username
            }
            user!!.updateProfile(profileChange)
            user.sendEmailVerification().await()

            emit(DataState.OnData(authenticate.user!!))
        }catch (e:Exception){
            emit(DataState.OnFailure("register failed ${e.message}"))
        }

    }.flowOn(dispatcherProvider.io())

    override suspend fun loginGoogle(idToken:String): Flow<DataState<FirebaseUser>> =flow {
        emit(DataState.OnLoading)
        try {
            val credential = GoogleAuthProvider.getCredential(idToken,null)
            val authenticate = firebaseAuth.signInWithCredential(credential).await()
            emit(DataState.OnData(authenticate.user!!))
        }catch (e:Exception){
            emit(DataState.OnFailure(e.message ?: "unknown error"))
        }
    }.flowOn(dispatcherProvider.io())

    override suspend fun registerFCMTokenAndSubscribeTopic() {
        val userId = firebaseAuth.currentUser?.uid ?: ""
        if(userId.isBlank()){
            return
        }

        firebaseMessaging.token.addOnCompleteListener {
                task->
            try {
                if(task.isSuccessful){
                    val token = task.result
                    firestore.collection("user")
                        .document(userId)
                        .set(
                            UserToken(
                                token = token,
                                updated_at = getNowMillis(),
                                created_at = getNowMillis()
                            ).toHashMap(),
                            SetOptions.merge()
                        )
                }

            }catch (ignored:Exception){

            }
        }
        firebaseMessaging.subscribeToTopic("berita")
            .await()
    }

    override suspend fun changePassword(newPassword: String): Flow<DataState<Boolean>> =flow {
        emit(DataState.OnLoading)
        val user =  firebaseAuth.currentUser
        if(user == null){
            emit(DataState.OnFailure("User note logged in!"))
        }else{
            try{
             val result =   user.updatePassword(newPassword)
                .await()
               emit(DataState.OnData(true))
            }catch (e:Exception){
                emit(DataState.OnFailure(e.message ?: "Password not changed!"))
            }
        }

    }.flowOn(dispatcherProvider.io())

    override suspend fun resetPasswordEmail(email: String): Flow<DataState<Boolean>> = flow {
        emit(DataState.OnLoading)
        try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            emit(DataState.OnData(true))
        }catch (e:Exception){
            emit(DataState.OnFailure(e.message ?: "Can't send new password to an email"))
        }
    }.flowOn(dispatcherProvider.io())

    override suspend fun getCurrentAppSetting(uid: String): Flow<AppSetting?> = appSettingDao.getApplicationSetting(uid).flowOn(dispatcherProvider.io())


    override suspend fun signOut(callback: () -> Unit) = withContext(dispatcherProvider.io()){
        taskDao.deleteAll()
        todoDao.deleteAll()
        firebaseAuth.signOut()
        callback.invoke()
    }

}