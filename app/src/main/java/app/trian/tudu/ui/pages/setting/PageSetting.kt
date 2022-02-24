package app.trian.tudu.ui.pages.setting

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.R
import app.trian.tudu.common.*
import app.trian.tudu.data.local.AppSetting
import app.trian.tudu.domain.ThemeData
import app.trian.tudu.ui.component.AppbarBasic
import app.trian.tudu.ui.component.dialog.DateTimeFormat
import app.trian.tudu.ui.component.dialog.DialogDateFormat
import app.trian.tudu.ui.component.dialog.DialogTimeFormat
import app.trian.tudu.ui.component.dialog.ModalBottomSheetPrivacyPolicy
import app.trian.tudu.ui.theme.HexToJetpackColor
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.UserViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import compose.icons.Octicons
import compose.icons.octicons.*
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun PageSetting(
    modifier: Modifier = Modifier,
    router: NavHostController,
    theme:String
) {
    val ctx = LocalContext.current
    val userViewModel = hiltViewModel<UserViewModel>()

    val systemUiController = rememberSystemUiController()
    val isSystemDark = isSystemInDarkTheme()
    val statusBar = MaterialTheme.colors.background

    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val appSetting by userViewModel.appSetting.observeAsState(initial = AppSetting())
    val currentUser by userViewModel.currentUser.observeAsState()
    var showDialogDateFormt by remember {
        mutableStateOf(false)
    }
    var showDialogTimeFormat by remember {
        mutableStateOf(false)
    }

    val menus = listOf(
        ItemSetting(
            name = stringResource(R.string.setting_account_setting),
            children = listOf(
                SubItemSetting(
                    name = stringResource(R.string.setting_sub_profile_information),
                    route = Routes.PAGE_USER_INFORMATION,
                    type = "link",
                    color = HexToJetpackColor.getColor(HexToJetpackColor.Blue),
                    icon = Octicons.Person24,
                    description = stringResource(R.string.setting_sub_description_profile_information)
                ),
                SubItemSetting(
                    name = stringResource(R.string.setting_sub_change_password),
                    route = Routes.CHANGE_PASSWORD,
                    color = HexToJetpackColor.getColor(HexToJetpackColor.Green),
                    icon = Octicons.Lock24,
                    description = stringResource(R.string.setting_sub_description_change_password)
                )
            )
        ),
        ItemSetting(
            name = stringResource(R.string.setting_notification_setting),
            children = listOf(
                SubItemSetting(
                    name = stringResource(R.string.setting_sub_push_notification),
                    route = "",
                    type = "",
                    color = HexToJetpackColor.getColor(HexToJetpackColor.Red),
                    icon = Octicons.Bell24,
                    description = stringResource(R.string.setting_sub_description_push_notification)
                ),
            )
        ),
        ItemSetting(
            name = stringResource(R.string.setting_date_time),
            children = listOf(
                SubItemSetting(
                    name = stringResource(R.string.setting_sub_date_format),
                    route = "date_format",
                    type = "button",
                    color = HexToJetpackColor.getColor(HexToJetpackColor.Blue),
                    icon = Octicons.Calendar24,
                    description = "November,22 2020"
                ),
                SubItemSetting(
                    name = stringResource(R.string.setting_sub_tim_format),
                    route = "time_format",
                    type = "button",
                    color = HexToJetpackColor.getColor(HexToJetpackColor.Blue),
                    icon = Octicons.Clock24,
                    description = "24 Hour"
                ),
            )
        ),
        ItemSetting(
            name = stringResource(R.string.setting_general),
            children = listOf(
                SubItemSetting(
                    name = stringResource(R.string.setting_sub_rate_our_app),
                    route = "rate_app",
                    type = "button",
                    color = HexToJetpackColor.getColor(HexToJetpackColor.Yellow),
                    icon = Octicons.Star24,
                    description = stringResource(R.string.setting_sub_description_rate_and_review)
                ),
                SubItemSetting(
                    name = stringResource(R.string.setting_sub_send_feedback),
                    route = "send_feedback",
                    type = "button",
                    color = HexToJetpackColor.getColor(HexToJetpackColor.Yellow),
                    icon = Octicons.Mail24,
                    description = stringResource(R.string.setting_sub_description_send_feedback)
                ),
                SubItemSetting(
                    name = stringResource(R.string.setting_sub_privacy_policy),
                    route = "privacy_policy",
                    type = "button",
                    color = HexToJetpackColor.getColor(HexToJetpackColor.Blue),
                    icon = Octicons.Unverified24,
                    description = stringResource(R.string.setting_sub_description_privacy_policy)
                ),
            )
        )
    )
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = statusBar,
            darkIcons = when(theme.getTheme()){
                ThemeData.DEFAULT -> !isSystemDark
                ThemeData.DARK -> false
                ThemeData.LIGHT -> true
            }
        )
    }
    LaunchedEffect(key1 = Unit, block = {
        userViewModel.getCurrentSetting()
    })

    DialogDateFormat(
        show = showDialogDateFormt,
        dateFormat = appSetting?.dateFormat ?: DateTimeFormat.YYYYMMDD.value,
        onDismiss = {
            showDialogDateFormt=false
        },
        onConfirm = {
            userViewModel.updateCurrentSetting(appSetting.apply { dateFormat = it.value })
            showDialogDateFormt=false
        }
    )
    DialogTimeFormat(
        show = showDialogTimeFormat,
        timeFormat = appSetting?.timeFormat ?: DateTimeFormat.TWENTY.value,
        onDismiss = {
            showDialogTimeFormat=false
        },
        onConfirm = {
            userViewModel.updateCurrentSetting(appSetting.apply { timeFormat = it.value })
            showDialogTimeFormat=false
        }
    )

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            ModalBottomSheetPrivacyPolicy{
                scope.launch {
                    bottomSheetState.hide()
                }
            }
        }
    ) {
        Scaffold(
            topBar ={
                AppbarBasic(title = "Settings"){
                    router.popBackStack()
                }
            }
        ) {
            LazyColumn(content = {
                item {
                    Spacer(modifier = modifier.height(40.dp))
                }
                items(menus){
                        menu->
                    ItemParentSetting(
                        item = menu,
                        appSetting = appSetting,
                        onClick = {
                            when(it){
                                "time_format"->{
                                    showDialogTimeFormat=true
                                }
                                "date_format"->{
                                    showDialogDateFormt=true
                                }
                                "privacy_policy"->{
                                    scope.launch{
                                        bottomSheetState.show()
                                    }
                                }
                                "send_feedback"->{
                                    ctx.emailTo(
                                        from = currentUser?.email ?: "",
                                        to =ctx.getString(R.string.email_feedback),
                                        subject =ctx.getString(R.string.subject_feedback)
                                    )
                                }
                                "rate_app"->{
                                    ctx.gotoApp()
                                }
                                else->{}
                            }
                        },
                        onNavigate = {
                            router.navigate(it)
                        }
                    )
                }
            })

        }
    }
}

@Composable
fun ItemParentSetting(
    modifier: Modifier=Modifier,
    item:ItemSetting,
    appSetting: AppSetting,
    onNavigate:(route:String)->Unit={},
    onClick:(route:String)->Unit={}
) {
    Column {
        Text(
            text = item.name,
            maxLines=1,
            overflow= TextOverflow.Ellipsis,
            style =MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                color = MaterialTheme.colors.primary
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = modifier.height(10.dp))
        item.children.forEach {
            ItemChildSetting(
                itemSetting = it,
                onClick = onClick,
                onNavigate = onNavigate,
                appSetting = appSetting
            )
        }
    }
}

@Composable
fun ItemChildSetting(
    modifier: Modifier=Modifier,
    itemSetting: SubItemSetting,
    appSetting: AppSetting,
    onNavigate:(route:String)->Unit={},
    onClick:(route:String)->Unit={}
){
    Column(
        modifier = modifier.clickable {
            if (itemSetting.type == "link") {
                if(itemSetting.route.isNotBlank()) {
                    onNavigate(itemSetting.route)
                }
            } else {

                    onClick(itemSetting.route)

            }
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()

        ) {
            Spacer(modifier =modifier.width(20.dp))
            Box(
                modifier = modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(itemSetting.color)
            ){
                Icon(
                    imageVector = Octicons.Person24,
                    contentDescription = "",
                    modifier=modifier.align(Alignment.Center),
                    tint = MaterialTheme.colors.background
                )
            }
            Spacer(modifier =modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = itemSetting.name,
                    maxLines=1,
                    overflow= TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                Text(
                    text = when(itemSetting.route){
                        "date_format"-> getNowMillis().toReadableDate(appSetting.dateFormat)
                        "time_format"-> "${if(appSetting.timeFormat == "DEFAULT") "Default System"  else appSetting.timeFormat+" Hours" } "
                        else->itemSetting.description
                    },
                    maxLines=1,
                    overflow= TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.caption.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light
                    )
                )
            }
        }
        Spacer(modifier = modifier.height(10.dp))
    }
    Divider()
    Spacer(modifier = modifier.height(20.dp))
}
data class ItemSetting(
    var name:String,
    var children:List<SubItemSetting>
)
data class SubItemSetting(
    var name:String,
    var type:String="link",
    var description:String,
    var route:String,
    var icon:ImageVector,
    var color: Color
)
@ExperimentalMaterialApi
@Preview(
    uiMode=UI_MODE_NIGHT_NO
)
@Preview(
    uiMode=UI_MODE_NIGHT_YES
)
@Composable
fun PreviewPageSetting() {
    TuduTheme {
        PageSetting(
            router = rememberNavController(),
            theme = ThemeData.DEFAULT.value
        )
    }
}