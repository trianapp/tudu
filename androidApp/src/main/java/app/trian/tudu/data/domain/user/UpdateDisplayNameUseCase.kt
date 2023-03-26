package app.trian.tudu.data.domain.user

import app.trian.tudu.data.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UpdateDisplayNameUseCase @Inject constructor(private val auth: FirebaseAuth) {
    operator fun invoke(displayName:String):Flow<Response<FirebaseUser>> = flow {
        emit(Response.Loading)
        val updateProfileChangeRequest = userProfileChangeRequest {
            this.displayName = displayName
        }
        auth.currentUser?.updateProfile(updateProfileChangeRequest)
        emit(Response.Result(auth.currentUser!!))
    }.flowOn(Dispatchers.IO)
}