package app.trian.tudu.data.domain.user

import app.trian.tudu.data.utils.Response
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val auth:FirebaseAuth
) {
    operator fun invoke(
        email:String
    ):Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        auth.sendPasswordResetEmail(email)
        emit(Response.Result(true))
    }.flowOn(Dispatchers.IO)
}