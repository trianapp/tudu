package app.trian.tudu.feature.dashboard.profile

import android.graphics.Bitmap
import app.trian.tudu.R
import app.trian.tudu.base.BaseViewModelData
import app.trian.tudu.base.extensions.getFirstDays
import app.trian.tudu.data.domain.task.GetStatisticChartTaskUseCase
import app.trian.tudu.data.domain.task.GetStatisticCountTaskUseCase
import app.trian.tudu.data.domain.user.GetUserProfileUseCase
import app.trian.tudu.data.domain.user.SignOutUseCase
import app.trian.tudu.data.domain.user.UpdateProfilePictureUseCase
import app.trian.tudu.data.utils.Response
import app.trian.tudu.feature.splash.Splash
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateProfilePictureUseCase: UpdateProfilePictureUseCase,
    private val getStatisticCountTaskUseCase: GetStatisticCountTaskUseCase,
    private val getStatisticChartTaskUseCase: GetStatisticChartTaskUseCase,
    private val signOutUseCase: SignOutUseCase
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

    private fun getChartData(date: LocalDate) = async {
        getStatisticChartTaskUseCase(date).collect {
            when (it) {
                is Response.Error -> Unit
                Response.Loading -> Unit
                is Response.Result -> commitData { copy(chartData = it.data) }
            }
        }
    }

    /** get statistic by date
     * when page load for the first time current date measured(search first day of the week
     */
    private fun getStatistic(isFirstLoad: Boolean = true, isNext: Boolean) = asyncWithState {
        val date = if (isFirstLoad) currentDate.getFirstDays().first
        else {
            if (isNext) currentDate.plusDays(6)
            else currentDate.minusDays(6)
        }
        commit { copy(currentDate = date) }
        getChartData(date)
    }

    private fun onProfilePictureChanged(picture: Bitmap?, cb: suspend () -> Unit) = async {
        when (picture) {
            null -> showSnackbar(R.string.text_message_failed_take_picture)
            else -> {
                commitData { copy(profilePicture = "", profileBitmap = picture) }
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
            Response.Loading -> showLoadingProfilePicture()
            is Response.Result -> hideLoadingProfilePicture()
        }
    }

    private fun signOut() = async {
        signOutUseCase().collect {
            when (it) {
                is Response.Error -> showSnackbar(it.message)
                Response.Loading -> Unit
                is Response.Result -> navigateAndReplaceAll(Splash.routeName)
            }
        }
    }

    override fun handleActions() = onEvent {
        when (it) {
            ProfileEvent.GetProfile -> {
                getCurrentUser()
                getCountTask()
                getStatistic(isFirstLoad = true, isNext = false)
            }

            is ProfileEvent.GetStatistic -> getStatistic(it.isFirstLoad, it.isNext)
            is ProfileEvent.SubmitProfilePicture -> onProfilePictureChanged(it.bitmap) {
                updateProfilePictureUseCase(it.bitmap).collect(::handleResponseProfilePicture)
            }
            ProfileEvent.SignOut -> signOut()
        }
    }

}