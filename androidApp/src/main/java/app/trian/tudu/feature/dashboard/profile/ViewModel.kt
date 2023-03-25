package app.trian.tudu.feature.dashboard.profile

import android.graphics.Bitmap
import app.trian.tudu.base.BaseViewModelData
import app.trian.tudu.base.extensions.getNextWeek
import app.trian.tudu.base.extensions.getPreviousWeek
import app.trian.tudu.data.domain.task.GetStatisticChartTaskUseCase
import app.trian.tudu.data.domain.task.GetStatisticCountTaskUseCase
import app.trian.tudu.data.domain.user.GetUserProfileUseCase
import app.trian.tudu.data.domain.user.UpdateProfilePictureUseCase
import app.trian.tudu.data.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateProfilePictureUseCase: UpdateProfilePictureUseCase,
    private val getStatisticCountTaskUseCase: GetStatisticCountTaskUseCase,
    private val getStatisticChartTaskUseCase: GetStatisticChartTaskUseCase
) : BaseViewModelData<ProfileState, ProfileDataState, ProfileEvent>(ProfileState(), ProfileDataState()) {
    init {
        handleActions()
    }

    private fun showLoadingProfilePicture() = commit { copy(isLoadingProfilePicture = true) }
    private fun hideLoadingProfilePicture() = commit { copy(isLoadingProfilePicture = false) }

    private fun getCurrentUser() = async {
        getUserProfileUseCase().collect {
            when (it) {
                is Response.Error -> Unit
                Response.Loading -> Unit
                is Response.Result -> {
                    commitData {
                        copy(
                            displayName = it.data.displayName.orEmpty(),
                            email = it.data.email.orEmpty(),
                            profilePicture = it.data.photoUrl.toString()
                        )
                    }
                }
            }
        }
    }

    private fun getCountTask() = async {
        getStatisticCountTaskUseCase().collect {
            when (it) {
                is Response.Error -> Unit
                Response.Loading -> Unit
                is Response.Result -> commitData {
                    copy(
                        totalAllTask = it.data.totalTask,
                        totalCompletedTask = it.data.completedTask,
                        totalUnCompletedTask = it.data.pendingTask
                    )
                }
            }
        }
    }

    private fun getChartData() = async {
        getStatisticChartTaskUseCase(uiState.value.selectedDate).collect {
            when (it) {
                is Response.Error -> Unit
                Response.Loading -> Unit
                is Response.Result -> {
                    commitData {
                        copy(
                            chartData = it.data
                        )
                    }
                }
            }
        }
    }

    private fun getStatistic(isNext: Boolean) = async {
        with(uiState.value) {
            val date = if (isNext) selectedDate.getNextWeek() else selectedDate.getPreviousWeek()
            getChartData()
            commit { copy(selectedDate = date) }
        }
    }

    private fun onProfilePictureChanged(picture: Bitmap?, cb: suspend () -> Unit) = async {
        when (picture) {
            null -> showSnackbar("Gagal mengambil gambar")
            else -> {
                commitData {
                    copy(
                        profilePicture = "",
                        profileBitmap = picture
                    )
                }
                cb()

            }
        }
    }

    private fun handleResponseProfilePicture(result: Response<String>) {
        when (result) {
            is Response.Error -> {
                showSnackbar(result.message)
                hideLoadingProfilePicture()
            }
            Response.Loading -> {
                showLoadingProfilePicture()
            }
            is Response.Result -> {
                hideLoadingProfilePicture()
            }
        }
    }

    override fun handleActions() = onEvent {
        when (it) {
            ProfileEvent.GetProfile -> {
                getCurrentUser()
                getCountTask()
            }

            is ProfileEvent.GetStatistic -> getStatistic(it.isNext)
            is ProfileEvent.SubmitProfilePicture -> onProfilePictureChanged(it.bitmap) {
                updateProfilePictureUseCase(it.bitmap).collect(::handleResponseProfilePicture)
            }
        }
    }

}