package app.trian.tudu.data.domain.user

import app.trian.tudu.data.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SignUpWithEmailAndPasswordUseCase @Inject constructor(
    private val auth: FirebaseAuth
) {
    operator fun invoke(
        displayName: String,
        email: String,
        password: String
    ): Flow<Response<FirebaseUser>> = flow {
        emit(Response.Loading)

        val authenticate = auth.createUserWithEmailAndPassword(
            email,
            password
        ).await()

        val user = auth.currentUser
        val profileChangeRequest = userProfileChangeRequest {
            this.displayName = displayName
        }
        user?.updateProfile(profileChangeRequest)
        user?.sendEmailVerification()

        emit(Response.Result(authenticate.user!!))
    }.flowOn(Dispatchers.IO)
}