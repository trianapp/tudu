package app.trian.tudu.feature.appSetting

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Doorbell
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.trian.tudu.ApplicationState
import app.trian.tudu.R
import app.trian.tudu.base.BaseMainApp
import app.trian.tudu.base.UIWrapper
import app.trian.tudu.base.extensions.emailTo
import app.trian.tudu.base.extensions.gotoApp
import app.trian.tudu.base.extensions.hideBottomSheet
import app.trian.tudu.components.AppbarBasic
import app.trian.tudu.components.BottomSheetPrivacyPolicy
import app.trian.tudu.components.DialogDateFormat
import app.trian.tudu.components.DialogTimeFormat
import app.trian.tudu.components.ItemParentSetting
import app.trian.tudu.components.ItemSetting
import app.trian.tudu.components.SubItemSetting
import app.trian.tudu.data.dateTime.DateFormat
import app.trian.tudu.data.dateTime.TimeFormat
import app.trian.tudu.feature.auth.changePassword.ChangePassword
import app.trian.tudu.feature.editProfile.EditProfile

object AppSetting {
    const val routeName = "AppSetting"
}

fun NavGraphBuilder.routeAppSetting(
    state: ApplicationState,
) {
    composable(AppSetting.routeName) {
        ScreenAppSetting(appState = state)
    }
}

@Composable
internal fun ScreenAppSetting(
    appState: ApplicationState,
) = UIWrapper<AppSettingViewModel>(appState = appState) {
    val menus = listOf(
        ItemSetting(
            name = stringResource(R.string.setting_account_setting),
            children = listOf(
                SubItemSetting(
                    name = stringResource(R.string.setting_sub_profile_information),
                    route = EditProfile.routeName,
                    type = "link",
                    icon = Icons.Outlined.Person,
                    description = stringResource(R.string.setting_sub_description_profile_information)
                ),
                SubItemSetting(
                    name = stringResource(R.string.setting_sub_change_password),
                    route = ChangePassword.routeName,
                    icon = Icons.Outlined.Lock,
                    description = stringResource(R.string.setting_sub_description_change_password)
                )
            )
        ),
        ItemSetting(
            name = stringResource(R.string.text_parent_setting_personalisation),
            children = listOf(
                SubItemSetting(
                    name = stringResource(R.string.menu_theme),
                    route = "theme",
                    type = "button",
                    icon = Icons.Outlined.Person,
                    description = stringResource(R.string.setting_sub_description_theme)
                ),
                SubItemSetting(
                    name = stringResource(R.string.setting_sub_push_notification),
                    route = "",
                    type = "",
                    icon = Icons.Outlined.Doorbell,
                    description = stringResource(R.string.setting_sub_description_push_notification)
                )
            )
        ),
        ItemSetting(
            name = stringResource(R.string.setting_general),
            children = listOf(
                SubItemSetting(
                    name = stringResource(R.string.setting_sub_rate_our_app),
                    route = "rate_app",
                    type = "button",
                    icon = Icons.Outlined.Star,
                    description = stringResource(R.string.setting_sub_description_rate_and_review)
                ),
                SubItemSetting(
                    name = stringResource(R.string.setting_sub_send_feedback),
                    route = "send_feedback",
                    type = "button",
                    icon = Icons.Outlined.Feedback,
                    description = stringResource(R.string.setting_sub_description_send_feedback)
                ),
                SubItemSetting(
                    name = stringResource(R.string.setting_sub_privacy_policy),
                    route = "privacy_policy",
                    type = "button",
                    icon = Icons.Outlined.Verified,
                    description = stringResource(R.string.setting_sub_description_privacy_policy)
                ),
                SubItemSetting(
                    name = stringResource(R.string.menu_faq),
                    route = "faq",
                    type = "button",
                    icon = Icons.Outlined.QuestionMark,
                    description = stringResource(R.string.setting_sub_description_privacy_policy)
                )
            )
        )
    )

    val state by uiState.collectAsState()
    val ctx = LocalContext.current
    with(appState) {
        hideBottomAppBar()
        setupTopAppBar {
            AppbarBasic(
                title = "Setting",
                onBackPressed = {
                    navigateUp()
                }
            )
        }
        setupBottomSheet {
            BottomSheetPrivacyPolicy(
                onAccept = {
                    hideBottomSheet()
                }
            )
        }
    }
    DialogDateFormat(
        show = state.showDialogDateFormat,
        dateFormat = state.dateFormat.ifEmpty { DateFormat.YYYYMMDD.value },
        onConfirm = {},
        onDismiss = {
            dispatch(AppSettingEvent.ShowDateFormat(false))
        }
    )
    DialogTimeFormat(
        show = state.showDialogTimeFormat,
        timeFormat = state.timeFormat.ifEmpty { TimeFormat.TWENTY.value },
        onConfirm = {},
        onDismiss = {
            dispatch(AppSettingEvent.ShowTimeFormat(false))
        }
    )
    LazyColumn(content = {
        items(menus) { menu ->
            ItemParentSetting(
                item = menu,
                appSetting = state,
                onClick = {
                    when (it) {
                        "time_format" -> {
                            dispatch(AppSettingEvent.ShowTimeFormat(true))
                        }

                        "date_format" -> {
                            dispatch(AppSettingEvent.ShowDateFormat(true))
                        }

                        "privacy_policy" -> {
                            showBottomSheet()
                        }

                        "send_feedback" -> {
                            ctx.emailTo(
                                from = "",
                                to = ctx.getString(R.string.email_feedback),
                                subject = ctx.getString(R.string.subject_feedback)
                            )
                        }

                        "rate_app" -> {
                            ctx.gotoApp()
                        }

                        else -> {}
                    }
                },
                onNavigate = {
                    if (it.isNotEmpty()) {
                        navigateSingleTop(it)
                    }
                }
            )
        }
    })
}


@Preview(
    apiLevel = Build.VERSION_CODES.S_V2,
)
@Preview(
    apiLevel = Build.VERSION_CODES.S_V2,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewScreenAppSetting() {
    BaseMainApp {
        ScreenAppSetting(it)
    }
}