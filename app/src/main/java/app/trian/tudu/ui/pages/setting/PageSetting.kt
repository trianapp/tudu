package app.trian.tudu.ui.pages.setting

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.common.Routes
import app.trian.tudu.ui.component.AppbarBasic
import app.trian.tudu.ui.component.dialog.DialogDateFormat
import app.trian.tudu.ui.component.dialog.DialogTimeFormat
import app.trian.tudu.ui.theme.HexToJetpackColor
import app.trian.tudu.ui.theme.TuduTheme
import com.squareup.okhttp.Route
import compose.icons.Octicons
import compose.icons.octicons.*

@Composable
fun PageSetting(
    modifier: Modifier = Modifier,
    router: NavHostController
) {
    var showDialogDateFormt by remember {
        mutableStateOf(false)
    }
    var showDialogTimeFormat by remember {
        mutableStateOf(false)
    }
    val menus = listOf(
        ItemSetting(
            name = "Account Setting",
            children = listOf(
                SubItemSetting(
                    name = "Profile Information",
                    route = "",
                    type = "",
                    color = HexToJetpackColor.getColor(HexToJetpackColor.Blue),
                    icon = Octicons.Person24,
                    description = "Name,Email,Bio"
                ),
                SubItemSetting(
                    name = "Change Password",
                    route = Routes.CHANGE_PASSWORD,
                    color = HexToJetpackColor.getColor(HexToJetpackColor.Green),
                    icon = Octicons.Lock24,
                    description = "Change your current password"
                )
            )
        ),
        ItemSetting(
            name = "Notification Setting",
            children = listOf(
                SubItemSetting(
                    name = "Push Notifications",
                    route = "",
                    type = "",
                    color = HexToJetpackColor.getColor(HexToJetpackColor.Red),
                    icon = Octicons.Bell24,
                    description = "New reminder for your tasks"
                ),
            )
        ),
        ItemSetting(
            name = "Date & Time",
            children = listOf(
                SubItemSetting(
                    name = "Date Format",
                    route = "date_format",
                    type = "button",
                    color = HexToJetpackColor.getColor(HexToJetpackColor.Blue),
                    icon = Octicons.Calendar24,
                    description = "November,22 2020"
                ),
                SubItemSetting(
                    name = "Time Format",
                    route = "time_format",
                    type = "button",
                    color = HexToJetpackColor.getColor(HexToJetpackColor.Blue),
                    icon = Octicons.Clock24,
                    description = "24 Hour"
                ),
            )
        ),
        ItemSetting(
            name = "General",
            children = listOf(
                SubItemSetting(
                    name = "Rate our App",
                    route = "",
                    type = "",
                    color = HexToJetpackColor.getColor(HexToJetpackColor.Yellow),
                    icon = Octicons.Star24,
                    description = "Rate & Review us"
                ),
                SubItemSetting(
                    name = "Send feedback",
                    route = "",
                    type = "",
                    color = HexToJetpackColor.getColor(HexToJetpackColor.Yellow),
                    icon = Octicons.Mail24,
                    description = "Share your thought"
                ),
                SubItemSetting(
                    name = "Privacy Policy",
                    route = "",
                    type = "",
                    color = HexToJetpackColor.getColor(HexToJetpackColor.Blue),
                    icon = Octicons.Unverified24,
                    description = "Read our privacy policy"
                ),
            )
        )
    )

    DialogDateFormat(
        show = showDialogDateFormt,
        onDismiss = {
                    showDialogDateFormt=false
        },
        onConfirm = {}
    )
    DialogTimeFormat(
        show = showDialogTimeFormat,
        onDismiss = {
                    showDialogTimeFormat=false
        },
        onConfirm = {}
    )
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
                    onClick = {
                        when(it){
                            "time_format"->{
                                showDialogTimeFormat=true
                            }
                            "date_format"->{
                                showDialogDateFormt=true
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

@Composable
fun ItemParentSetting(
    modifier: Modifier=Modifier,
    item:ItemSetting,
    onNavigate:(route:String)->Unit={},
    onClick:(route:String)->Unit={}
) {
    Column {
        Text(
            text = item.name,
            maxLines=1,
            overflow= TextOverflow.Ellipsis,
            style =TextStyle(
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
                onNavigate = onNavigate
            )
        }
    }
}

@Composable
fun ItemChildSetting(
    modifier: Modifier=Modifier,
    itemSetting: SubItemSetting,
    onNavigate:(route:String)->Unit={},
    onClick:(route:String)->Unit={}
){
    Column(
        modifier = modifier.clickable {
            if (itemSetting.type == "link") {
                onNavigate(itemSetting.route)
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
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                Text(
                    text = itemSetting.description,
                    maxLines=1,
                    overflow= TextOverflow.Ellipsis,
                    style = TextStyle(
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
@Preview(
    uiMode=UI_MODE_NIGHT_NO
)
@Preview(
    uiMode=UI_MODE_NIGHT_YES
)
@Composable
fun PreviewPageSetting() {
    TuduTheme {
        PageSetting(router = rememberNavController())
    }
}