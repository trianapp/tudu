package app.trian.tudu.ui.pages.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
                    route = "",
                    type = "button",
                    color = HexToJetpackColor.getColor(HexToJetpackColor.Blue),
                    icon = Octicons.Calendar24,
                    description = "November,22 2020"
                ),
                SubItemSetting(
                    name = "Time Format",
                    route = "",
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
                    onClick = {},
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
                .padding(horizontal = 30.dp)
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
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth().clickable {
            if(itemSetting.type == "link"){
                onNavigate(itemSetting.route)
            }else{
                onClick(itemSetting.route)
            }
        }
    ) {
        Spacer(modifier =modifier.width(30.dp))
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
    Spacer(modifier = modifier.height(5.dp))
    Divider()
    Spacer(modifier = modifier.height(10.dp))
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
@Preview
@Composable
fun PreviewPageSetting() {
    TuduTheme {
        PageSetting(router = rememberNavController())
    }
}