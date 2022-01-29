package app.trian.tudu.data.repository

import app.trian.tudu.common.DispatcherProvider
import app.trian.tudu.data.repository.design.UserRepository
import app.trian.tudu.domain.DataState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.asTask
import kotlinx.coroutines.tasks.await
import logcat.logcat

class UserRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val firebaseAuth: FirebaseAuth
):UserRepository {
    override suspend fun checkIsUserLogin(): Flow<Boolean> = flow {
        val currentUser = firebaseAuth.currentUser
        if(currentUser == null) {
            emit(false)
        }else{
            emit(true)
        }
    }.flowOn(dispatcherProvider.io())

    override suspend fun loginBasic(email: String, password: String): Flow<DataState<FirebaseUser>> = flow {
        emit(DataState.LOADING)
        try {
            val authenticate = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(DataState.onData(authenticate.user!!))
        }catch (e:Exception){
            emit(DataState.onFailure("authentication failed because ${e.message}"))
        }
    }.flowOn(dispatcherProvider.io())

    override suspend fun registerBasic(email:String,password:String): Flow<DataState<FirebaseUser>> =flow {
        emit(DataState.LOADING)
        try {
            val authenticate = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
            emit(DataState.onData(authenticate.user!!))
        }catch (e:Exception){
            emit(DataState.onFailure("register failed ${e.message}"))
        }

    }.flowOn(dispatcherProvider.io())

    override suspend fun loginGoogle(): Flow<DataState<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun registerGoogle(): Flow<DataState<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(callback: () -> Unit) {
        firebaseAuth.signOut()
        callback.invoke()
    }

}