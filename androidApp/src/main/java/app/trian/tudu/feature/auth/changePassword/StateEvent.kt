package app.trian.tudu.feature.auth.changePassword

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class ChangePasswordState(
    var newPassword: String = "",
    var confirmPassword: String = ""
) : Parcelable

@Immutable
sealed class ChangePasswordEvent {
    object Submit : ChangePasswordEvent()
}