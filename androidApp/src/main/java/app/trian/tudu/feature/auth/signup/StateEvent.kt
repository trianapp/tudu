package app.trian.tudu.feature.auth.signup

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class SignUpState(
    val agreeTnc:Boolean=false,
    val displayName: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading:Boolean=false
) : Parcelable

@Immutable
sealed class SignUpEvent {
    object SignUpWithEmail : SignUpEvent()

}