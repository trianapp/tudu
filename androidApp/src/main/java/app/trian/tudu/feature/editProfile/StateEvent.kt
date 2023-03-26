package app.trian.tudu.feature.editProfile

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class EditProfileState(
    val displayName: String = "",
    val isLoading:Boolean=false
) : Parcelable

@Immutable
sealed class EditProfileEvent {
    object Submit : EditProfileEvent()
}