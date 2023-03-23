package app.trian.tudu.feature.inputNote

import androidx.lifecycle.SavedStateHandle
import app.trian.tudu.base.BaseViewModel
import app.trian.tudu.data.sdk.task.TaskSDK
import app.trian.tudu.data.utils.Response
import app.trian.tudu.feature.inputNote.InputNoteEvent.SetTaskNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class InputNoteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val taskSDK: TaskSDK
) : BaseViewModel<InputNoteState, InputNoteEvent>(InputNoteState()) {
    init {
        handleActions()
        getDetailTask(getTaskId())
    }
    private fun getTaskId() = savedStateHandle.get<String>(InputNote.keyArgs).orEmpty()

    private fun getDetailTask(taskId: String) = async {
        taskSDK.getDetailTask(taskId)
            .catch { }
            .collect {
                when (it) {
                    is Response.Error -> showSnackbar("Failed to load task")
                    Response.Loading -> Unit
                    is Response.Result -> commit {
                        copy(
                            taskName = it.data.taskName,
                            taskId = it.data.taskId,
                            taskNote = it.data.taskNote
                        )
                    }
                }
            }
    }

    private fun updateTaskNote() = async {

        taskSDK.updateTaskNote(
            taskId = getTaskId(),
            taskNote = uiState.value.taskNote,
            updatedAt = LocalDate.now().toString()
        )
            .catch { }
            .collect {
                when (it) {
                    is Response.Error -> {
                        showSnackbar("Failed to save")
                        commit {
                            copy(
                                isUpdateNote = false,
                                showDialogBackConfirmation = false
                            )
                        }
                    }

                    Response.Loading -> Unit
                    is Response.Result -> {
                        showSnackbar("Success update note!")
                        commit {
                            copy(
                                isUpdateNote = false,
                                showDialogBackConfirmation = false
                            )
                        }
                    }
                }
            }
    }

    override fun handleActions() = onEvent {
        when (it) {
            is SetTaskNote -> {
                if (uiState.value.isUpdateNote) {
                    commit { copy(taskNote = it.note) }
                } else {
                    commit {
                        copy(
                            taskNote =it.note,
                            isUpdateNote = true
                        )
                    }
                }
            }

            InputNoteEvent.CheckBackPressed -> if (uiState.value.isUpdateNote) {
                commit {
                    copy(
                        showDialogBackConfirmation = true
                    )
                }
            } else {
                navigateUp()
            }

            InputNoteEvent.SubmitNote -> updateTaskNote()
            InputNoteEvent.GetDetailTask -> getDetailTask(getTaskId())
        }
    }

}