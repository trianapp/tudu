package app.trian.tudu.feature.inputNote

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class InputNoteState(
    val taskNote:String="",
    val taskId:String="",
    val taskName:String="",

    val isUpdateNote:Boolean=false,

    val showDialogBackConfirmation:Boolean=false

) : Parcelable

sealed class InputNoteEvent{
    class SetTaskNote(val note:String): InputNoteEvent()

    object GetDetailTask: InputNoteEvent()

    object SubmitNote: InputNoteEvent()

    object CheckBackPressed: InputNoteEvent()
}