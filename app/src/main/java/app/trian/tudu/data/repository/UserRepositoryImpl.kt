package app.trian.tudu.data.repository

import app.trian.tudu.common.DispatcherProvider
import app.trian.tudu.common.getNowMillis
import app.trian.tudu.data.local.dao.TaskDao
import app.trian.tudu.data.local.dao.TodoDao
import app.trian.tudu.data.repository.design.UserRepository
import app.trian.tudu.domain.DataState
import app.trian.tudu.domain.UserToken
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.asTask
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import logcat.logcat

class UserRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val taskDao: TaskDao,
    private val todoDao: TodoDao
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
        emit(DataState.LOADING)
        try {
            val authenticate = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(DataState.onData(authenticate.user!!))
        }catch (e:Exception){
            emit(DataState.onFailure("authentication failed because ${e.message}"))
        }
    }.flowOn(dispatcherProvider.io())

    override suspend fun registerBasic(username:String,email:String,password:String): Flow<DataState<FirebaseUser>> =flow {
        emit(DataState.LOADING)
        try {
            val authenticate = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
            //
            val user = firebaseAuth.currentUser
            val profileChange = userProfileChangeRequest {
                displayName = username
            }
            user!!.updateProfile(profileChange)
            emit(DataState.onData(authenticate.user!!))
        }catch (e:Exception){
            emit(DataState.onFailure("register failed ${e.message}"))
        }

    }.flowOn(dispatcherProvider.io())

    override suspend fun loginGoogle(idToken:String): Flow<DataState<FirebaseUser>> =flow {
        emit(DataState.LOADING)
        try {
            val credential = GoogleAuthProvider.getCredential(idToken,null)
            val authenticate = firebaseAuth.signInWithCredential(credential).await()
            emit(DataState.onData(authenticate.user!!))
        }catch (e:Exception){
            emit(DataState.onFailure(e.message ?: "unknown error"))
        }
    }.flowOn(dispatcherProvider.io())

    override  fun registerNewToken(token:String) {
        try {
            val user = firebaseAuth.currentUser
            firestore.collection("userToken")
                .document(user!!.uid)
                .set(
                    UserToken(
                        token = token,
                        created_at = getNowMillis(),
                        updated_at = getNowMillis()
                    ),
                    SetOptions.merge()
                )

        }catch (e:Exception){

        }
    }


    override suspend fun signOut(callback: () -> Unit) = withContext(dispatcherProvider.io()){
        taskDao.deleteAll()
        todoDao.deleteAll()
        firebaseAuth.signOut()
        callback.invoke()
    }

}