package app.trian.tudu.feature.auth.signup

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class SignUpState(
    var agreeTnc:Boolean=false,
    var displayName: String = "",
    var email: String = "",
    var password: String = ""
) : Parcelable

@Immutable
sealed class SignUpEvent {
    object SignUpWithEmail : SignUpEvent()

}