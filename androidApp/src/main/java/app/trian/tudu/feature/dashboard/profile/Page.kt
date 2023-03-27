package app.trian.tudu.feature.dashboard.profile

import android.Manifest
import android.os.Build.VERSION_CODES
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize.Min
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.ModeEdit
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.trian.tudu.ApplicationState
import app.trian.tudu.R
import app.trian.tudu.base.BaseMainApp
import app.trian.tudu.base.UIWrapper
import app.trian.tudu.base.checkGrantedPermissionFrom
import app.trian.tudu.base.extensions.getHeaderChart
import app.trian.tudu.base.getBitmap
import app.trian.tudu.components.ButtonIcon
import app.trian.tudu.components.DialogConfirmation
import app.trian.tudu.components.DialogTakePicture
import app.trian.tudu.components.TuduBottomNavigation
import app.trian.tudu.components.chart.BarChartView
import app.trian.tudu.feature.appSetting.AppSetting
import app.trian.tudu.feature.editProfile.EditProfile
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest.Builder
import coil.size.Scale

object Profile {
    const val routeName = "Profile"
}

fun NavGraphBuilder.routeProfile(
    state: ApplicationState,
) {
    composable(Profile.routeName) {
        ScreenProfile(appState = state)
    }
}

@Composable
internal fun ScreenProfile(
    appState: ApplicationState,
) = UIWrapper<ProfileViewModel>(appState = appState) {
    val ctx = LocalContext.current
    fun version(): String = try {
        ctx.packageManager.getPackageInfo(ctx.packageName, 0).versionName
    } catch (e: Exception) {
        "0"
    }

    val listPermission = arrayOf(
        Manifest.permission.CAMERA
    )
    val state by uiState.collectAsState()
    val dataState by uiDataState.collectAsState()

    val painterProfilePicture = rememberAsyncImagePainter(
        Builder(LocalContext.current)
            .data(data = dataState.profilePicture)
            .placeholder(R.drawable.dummy_avatar)
            .error(R.drawable.dummy_avatar)
            .scale(Scale.FILL)
            .crossfade(true)
            .build()
    )

    val openCameraContract = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { image ->
            dispatch(ProfileEvent.SubmitProfilePicture(image))
        }
    )
    val openGalleryContract = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { imageUri ->
            val image = imageUri?.getBitmap(ctx.contentResolver)
            dispatch(ProfileEvent.SubmitProfilePicture(image))
        }
    )
    val requestPermissionContract = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            if (it) {
                openCameraContract.launch()
            }
        }
    )
    with(appState) {
        hideTopAppBar()
        setupBottomAppBar {
            TuduBottomNavigation(appState = appState)
        }
    }
    DialogConfirmation(
        show = state.showDialogRequestPermission,
        message = stringResource(R.string.text_message_need_permission_camera),
        onCancel = {
            commit { copy(showDialogRequestPermission = false) }
        },
        onConfirm = {
            commit { copy(showDialogRequestPermission = false) }
            requestPermissionContract.launch(
                Manifest.permission.CAMERA
            )
        }
    )

    DialogConfirmation(
        show = state.showDialogSignOutConfirmation,
        message = stringResource(R.string.text_message_signout_confrimation),
        onCancel = {
            commit { copy(showDialogSignOutConfirmation = false) }
        },
        onConfirm = {
            commit { copy(showDialogSignOutConfirmation = false) }
            dispatch(ProfileEvent.SignOut)
        }
    )

    DialogTakePicture(
        show = state.showDialogTakePicture,
        title = stringResource(R.string.text_title_dialog_take_picture_method),
        dismiss = {
            commit { copy(showDialogTakePicture = false) }
        },
        openCamera = {
            commit { copy(showDialogTakePicture = false) }
            if (listPermission.checkGrantedPermissionFrom(ctx)) {
                openCameraContract.launch()
            } else {
                commit { copy(showDialogRequestPermission = true) }
            }
        },
        openGallery = {
            runSuspend {
                commit { copy(showDialogTakePicture = false) }
                openGalleryContract.launch("image/*")
            }
        }
    )

    LaunchedEffect(key1 = this, block = {
        dispatch(ProfileEvent.GetProfile)
    })
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(
                        120.dp
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        if (!state.isLoadingProfilePicture) {
                            commit { copy(showDialogTakePicture = true) }
                        }
                    }
            ) {
                if (dataState.profileBitmap == null) {
                    Image(
                        painter = painterProfilePicture,
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Image(
                        bitmap = dataState.profileBitmap!!.asImageBitmap(),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Icon(
                    imageVector = Outlined.Edit,
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.primary),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                if (state.isLoadingProfilePicture) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Color.DarkGray.copy(
                                    alpha = 0.8f
                                )
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }

                }
            }
            IconButton(
                onClick = {
                    commit { copy(showDropdownMoreOption = true) }
                }
            ) {
                DropdownMenu(
                    expanded = state.showDropdownMoreOption,
                    onDismissRequest = {
                        commit { copy(showDropdownMoreOption = false) }
                    }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(text = stringResource(R.string.text_option_edit_profile))
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Outlined.ModeEdit,
                                contentDescription = ""
                            )
                        },
                        onClick = {
                            commit { copy(showDropdownMoreOption = false) }
                            navigateSingleTop(EditProfile.routeName)
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = stringResource(R.string.menu_settings))
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Outlined.Settings,
                                contentDescription = ""
                            )
                        },
                        onClick = {
                            commit { copy(showDropdownMoreOption = false) }
                            navigateSingleTop(AppSetting.routeName)
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = stringResource(R.string.menu_donate))
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Outlined.MonetizationOn, contentDescription = ""
                            )
                        },
                        onClick = {
                            showSnackbar(R.string.text_message_coming_soon)
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text(text = stringResource(R.string.btn_signout))
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Outlined.Logout,
                                contentDescription = ""
                            )
                        },
                        onClick = {
                            commit {
                                copy(
                                    showDialogSignOutConfirmation = true,
                                    showDropdownMoreOption = false
                                )
                            }
                        }
                    )
                }
                Icon(
                    imageVector = Outlined.MoreVert,
                    contentDescription = ""
                )
            }

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = dataState.displayName.ifEmpty { stringResource(R.string.text_user_display_name_placeholder) },
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = dataState.email.ifEmpty { stringResource(R.string.text_user_email_placeholder) },
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.tertiary
            )
        }

        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            ButtonIcon(
                fullWidth = true,
                text = stringResource(R.string.btn_share_profile),
                icon = Outlined.Share,
                onClick = {
                    showSnackbar(R.string.text_message_coming_soon)
                }
            )
        }
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(Min)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(R.string.text_total_task),
                )
                Text(
                    text = dataState.totalAllTask.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Divider(
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxHeight(fraction = 0.6f)
                    .width(1.dp)
            )
            Column {
                Text(
                    text = stringResource(R.string.text_total_completed_task),
                )
                Text(
                    text = dataState.totalCompletedTask.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Divider(
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxHeight(fraction = 0.6f)
                    .width(1.dp)
            )
            Column {
                Text(text = stringResource(R.string.text_total_todo))
                Text(
                    text = dataState.totalUnCompletedTask.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(
            modifier = Modifier
                .height(10.dp)
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {
            BarChartView(
                title = state.currentDate.getHeaderChart(),
                items = dataState.chartData.items,
                labels = dataState.chartData.labels,
                maxAxis = dataState.chartData.max,
                onArrowClicked = {
                    dispatch(ProfileEvent.GetStatistic(it, false))
                }
            )
        }
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        Text(
            text = stringResource(R.string.app_version, version()),
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
    }

}


@RequiresApi(VERSION_CODES.O)
@Preview
@Composable
fun PreviewScreenProfile() {
    BaseMainApp {
        ScreenProfile(it)
    }
}