package app.trian.tudu.data.domain.user;

import app.trian.tudu.data.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SignInWithEmailAndPasswordUseCase @Inject constructor(private val auth: FirebaseAuth) {
    operator fun invoke(email: String, password: String): Flow<Response<FirebaseUser>> = flow {
        emit(Response.Loading)
        try {
            val user = auth.signInWithEmailAndPassword(
                email, password
            ).await().user

            when {
                user == null -> emit(Response.Error(""))
                user.isEmailVerified -> emit(Response.Error(""))
                else -> emit(Response.Result(user))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.message.orEmpty()))
        }

    }.flowOn(Dispatchers.IO)
}
