package app.trian.tudu.data.domain.user

import app.trian.tudu.data.utils.Response
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val auth: FirebaseAuth
) {
    operator fun invoke(newPassword: String): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        val user = auth.currentUser
        user?.updatePassword(newPassword)
            ?.await()
        emit(Response.Result(true))
    }.flowOn(Dispatchers.IO)
}