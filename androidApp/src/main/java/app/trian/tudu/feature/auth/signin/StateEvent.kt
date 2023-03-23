package app.trian.tudu.feature.auth.signin

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class SignInState(
    val email: String = "",
    val password: String = ""
) : Parcelable

@Immutable
sealed class SignInEvent {
    object SignInWithEmail : SignInEvent()
    data class SignInWithGoogle(var result: Task<GoogleSignInAccount>?) : SignInEvent()
}