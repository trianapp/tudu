package app.trian.tudu.ui.component.drawer

import android.content.pm.PackageInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.trian.tudu.R
import app.trian.tudu.common.Routes
import app.trian.tudu.ui.theme.TuduTheme
import com.google.firebase.auth.FirebaseUser
import compose.icons.Octicons
import compose.icons.octicons.LogoGithub16
import compose.icons.octicons.SignOut24

/**
 * Drawer
 * author Trian Damai
 * created_at 01/02/22 - 08.41
 * site https://trian.app
 */

@Composable
fun DrawerContent(
    modifier: Modifier=Modifier,
    currentUser: FirebaseUser?,
    onClick: (item: ItemMenuDrawer) -> Unit={},
    onNavigate:(route:String)->Unit={}
) {
    val ctx = LocalContext.current
    fun getVersion():String{
        return try {
            ctx.packageManager.getPackageInfo(ctx.packageName,0).versionName
        }catch (e:Exception){
            "0"
        }
    }
    val menu = listOf(
        ItemMenuDrawer(
            name = "Give Rating",
            route = ""
        ),
        ItemMenuDrawer(
            name = "Theme",
            route = ""
        ),
        ItemMenuDrawer(
            name = "Widget",
            route = ""
        ),
        ItemMenuDrawer(
            name = "Donate",
            route = ""
        ),
        ItemMenuDrawer(
            name = "Feedback",
            route = ""
        ),
        ItemMenuDrawer(
            name = "FAQ",
            route = ""
        ),
        ItemMenuDrawer(
            name = "Settings",
            route = Routes.SETTING
        )
    )

    Column(
        modifier=modifier.fillMaxHeight().background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier=modifier
                .padding(
                    end = 30.dp,
                    start = 30.dp,
                    top = 25.dp
                )
        ) {
            Text(
                text = "Hi!",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            )
            Text(
                text = currentUser?.displayName ?: "Unknown",
                style = TextStyle(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            )
        }

        Column {
            menu.forEachIndexed {
                index,data->
                ItemDrawer(
                    item=data,
                    selected = index ==0,
                    onClick = {
                        if(it.type == "button"){
                            onClick(it)
                        }else{
                            onNavigate(it.route)
                        }
                    }
                )
            }
        }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    vertical = 10.dp
                )
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        onClick(
                            ItemMenuDrawer(
                                name = "",
                                route = "logout",
                                type = "button"
                            )
                    )
                }.padding(
                        vertical = 10.dp
                    )
            ) {
                Spacer(modifier = modifier.width(30.dp))
                Icon(
                    imageVector = Octicons.SignOut24,
                    contentDescription = "",
                    tint = MaterialTheme.colors.onBackground
                )
                Spacer(modifier = modifier.width(10.dp))
                Text(
                    text = stringResource(R.string.btn_logout),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.onBackground
                    )
                )
            }
            Spacer(modifier = modifier.height(30.dp))
            Text(
                text = "Version ${getVersion()}",
                style=TextStyle(
                    color = MaterialTheme.colors.onBackground

                ),
                modifier=modifier.padding(
                            vertical = 10.dp,
                            horizontal = 30.dp
                )
            )
        }
    }
}

@Composable
fun ItemDrawer(
    modifier: Modifier=Modifier,
    item:ItemMenuDrawer,
    selected:Boolean=false,
    onClick:(item:ItemMenuDrawer)->Unit={}
) {
    Spacer(modifier = modifier.height(4.dp))
    Row(
        modifier= modifier
            .fillMaxWidth()
            .clickable {
                onClick(item)
            }.padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(selected){
            Box(modifier = modifier
                .height(30.dp)
                .width(4.dp)
                .background(MaterialTheme.colors.primary)
            )
            Spacer(modifier = modifier.width(26.dp))
        }else{
            Spacer(modifier = modifier.width(30.dp))
        }

        Text(
            text = item.name,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.onBackground
            )
        )
    }
    Spacer(modifier = modifier.height(10.dp))
}

data class ItemMenuDrawer(
    var name:String,
    var route:String,
    var type:String="link"
)


@Preview
@Composable
fun PreviewDrawerContent() {
    TuduTheme {
        DrawerContent(
            currentUser = null
        )
    }
}

@Preview
@Composable
fun PreviewItemDrawer() {
    TuduTheme {
        ItemDrawer(
            item = ItemMenuDrawer(name = "Give Rating", route = ""),

        )
    }
}