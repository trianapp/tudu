package app.trian.tudu.feature.dashboard.profile

import app.trian.tudu.base.BaseViewModelData
import app.trian.tudu.base.extensions.getNextWeek
import app.trian.tudu.base.extensions.getPreviousWeek
import app.trian.tudu.data.sdk.auth.AuthSDK
import app.trian.tudu.data.sdk.task.TaskSDK
import app.trian.tudu.data.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authSDK: AuthSDK,
    private val taskSDK: TaskSDK
) : BaseViewModelData<ProfileState, ProfileDataState, ProfileEvent>(ProfileState(), ProfileDataState()) {
    init {
        handleActions()
    }

    private fun getCurrentUser() = async {
        authSDK.getCurrentUser().collect {
                when (it) {
                    is Response.Error -> Unit
                    Response.Loading -> Unit
                    is Response.Result -> {
                        commitData {
                            copy(
                                displayName = it.data?.displayName.orEmpty(),
                                email = it.data?.email.orEmpty(),
                                profilePicture = it.data?.photoUrl?.toString().orEmpty()
                            )
                        }
                    }
                }
            }
    }

    private fun getCountTask() = async {
        taskSDK.getStatisticTask().collect {
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
        taskSDK.getChartTask(uiState.value.selectedDate).collect {
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

    override fun handleActions() = onEvent {
        when (it) {
            ProfileEvent.GetProfile -> {
                getCurrentUser()
                getCountTask()
            }

            is ProfileEvent.GetStatistic -> getStatistic(it.isNext)

        }
    }

}