package app.trian.tudu.data.domain.user

import app.trian.tudu.data.utils.Response
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val auth: FirebaseAuth,
) {
    operator fun invoke(result: Task<GoogleSignInAccount>?): Flow<Response<FirebaseUser>> = flow {
        emit(Response.Loading)
        val task = result?.await()

        if (task == null) emit(Response.Error("Cancel by provider"))
        else emit(process(task.idToken.orEmpty()))
    }.flowOn(Dispatchers.IO)

    private suspend fun process(idToken: String): Response<FirebaseUser> {
        return try {
            val user = auth.signInWithCredential(
                GoogleAuthProvider.getCredential(idToken, null)
            ).await().user

            if (user == null) Response.Error("")
            else Response.Result(user)
        } catch (e: Exception) {
            Response.Error(e.message.orEmpty())
        }
    }
}