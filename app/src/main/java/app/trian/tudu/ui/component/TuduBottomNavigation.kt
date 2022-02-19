package app.trian.tudu.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle

import androidx.navigation.compose.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import app.trian.tudu.R
import app.trian.tudu.common.Routes
import app.trian.tudu.ui.theme.InactiveText
import app.trian.tudu.ui.theme.TuduTheme
import compose.icons.Octicons
import compose.icons.octicons.*

@Composable
fun TuduBottomNavigation(
    router: NavHostController,
    onButton:()->Unit={}
){
    val navItems = listOf(
        BottomNavigationScreenItem.Drawer,
        BottomNavigationScreenItem.Home,
        BottomNavigationScreenItem.Calender,
        BottomNavigationScreenItem.Profile,
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.primary
    ) {
        val navBackStackEntry by router.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        navItems.forEach {
            val selected = currentRoute == it.route
            if(it.type == "button"){
                IconToggleButton(
                    checked = false,
                    onCheckedChange = {
                        onButton()
                    }) {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.contentDescription,
                        tint =  if(selected) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
                    )
                }
            }else{
                BottomNavigationItem(
                    label={
                        Text(
                            text = stringResource(id = it.name),
                            style = MaterialTheme.typography.body2.copy(
                                if(selected) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
                            )
                        )
                    },
                    selected = if(it.type == "button") false else selected,
                    onClick = {
                        if (!selected) {
                            router.navigate(it.route)
                        }
                    },
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = InactiveText,
                    icon = {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = it.contentDescription,
                            tint  = if(selected) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
                        )
                    }
                )
            }
        }
    }
}

sealed class BottomNavigationScreenItem(
        var name:Int,
        var icon:ImageVector,
        var route:String ="",
        var type:String="link",
        var contentDescription:String=""
    ){
    object Drawer:BottomNavigationScreenItem(
        name=R.string.nav_item_drawer,
        icon=Octicons.ThreeBars16,
        type = "button",
        contentDescription = "Open Drawer Menu"
    )
    object Home:BottomNavigationScreenItem(
        R.string.nav_item_home,
        Octicons.Home24,
        Routes.Dashboard.HOME,
        type = "link",
        contentDescription = "Menu Home"
    )
    object Calender:BottomNavigationScreenItem(
        R.string.nav_item_calendar,
        Octicons.Calendar24,
        Routes.Dashboard.CALENDER,
        type = "link",
        contentDescription = "Menu Calendar"
    )
    object Profile:BottomNavigationScreenItem(
        R.string.nav_item_profile,
        Octicons.Person24,
        Routes.Dashboard.PROFILE,
        type = "link",
        contentDescription = "Menu My Profile"
    )
}

@Preview(
    uiMode=UI_MODE_NIGHT_YES
)
@Preview(
    uiMode=UI_MODE_NIGHT_NO
)
@Composable
fun PreviewTuduBottomNavigation(){
    TuduTheme {
        TuduBottomNavigation(router = rememberNavController())
    }
}