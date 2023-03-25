package app.trian.tudu.feature.dashboard.profile

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import app.trian.tudu.data.model.ChartModelData
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.time.LocalDate
import java.time.LocalTime

@Parcelize
@Immutable
data class ProfileState(
    val showDropdownMoreOption:Boolean=false,
    val currentDate:LocalDate= LocalDate.now(),

    val showDialogTakePicture:Boolean = false,
    val showDialogRequestPermission:Boolean = false,
    val isLoadingProfilePicture:Boolean=false
): Parcelable

@Parcelize
@Immutable
data class ProfileDataState(
    val profilePicture:String="",
    val profileBitmap:Bitmap?=null,
    val displayName:String="",
    val email:String="",
    val totalAllTask:Int=0,
    val totalCompletedTask:Int=0,
    val totalUnCompletedTask:Int=0,
    val currentDate:LocalTime = LocalTime.now(),
    val chartData:@RawValue ChartModelData=ChartModelData()
) : Parcelable

@Immutable
sealed class ProfileEvent{
    object GetProfile:ProfileEvent()
    object SignOut:ProfileEvent()

    class GetStatistic(val isNext:Boolean,val isFirstLoad:Boolean):ProfileEvent()

    data class SubmitProfilePicture(val bitmap: Bitmap?):ProfileEvent()
}