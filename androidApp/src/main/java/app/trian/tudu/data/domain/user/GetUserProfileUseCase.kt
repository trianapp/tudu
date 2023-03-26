package app.trian.tudu.data.domain.user

import app.trian.tudu.data.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val auth:FirebaseAuth
) {
    operator fun invoke():Flow<Response<FirebaseUser>> = flow {
        emit(Response.Loading)
        when (val user = auth.currentUser) {
            null -> emit(Response.Error(""))
            else -> emit(Response.Result(user))
        }

    }.flowOn(Dispatchers.IO)
}