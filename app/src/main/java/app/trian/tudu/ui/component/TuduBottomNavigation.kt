package app.trian.tudu.ui.component

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
        backgroundColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        val navBackStackEntry by router.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        navItems.forEach {
            val selected = currentRoute == it.route
            BottomNavigationItem(
                label={
                      Text(
                          text = stringResource(id = it.name),
                          style = TextStyle(
                              color = if(selected) MaterialTheme.colorScheme.primary else InactiveText
                          )
                      )
                },
                selected = if(it.type == "button") false else selected,
                onClick = {
                    if(it.type == "button"){
                        onButton()
                    }else {
                        if (!selected) {
                            router.navigate(it.route)
                        }
                    }

                },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = InactiveText,
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = "",
                        tint =if(selected) MaterialTheme.colorScheme.primary else InactiveText
                    )
                }
            )
        }
    }
}

sealed class BottomNavigationScreenItem(
        var name:Int,
        var icon:ImageVector,
        var route:String,
        var type:String="link"
    ){
    object Drawer:BottomNavigationScreenItem(
        R.string.nav_item_home,
        Octicons.ThreeBars16,
        "",
        type = "button"
    )
    object Home:BottomNavigationScreenItem(
        R.string.nav_item_home,
        Octicons.Home24,

        Routes.Dashboard.HOME,
        type = "link"
    )
    object Calender:BottomNavigationScreenItem(
        R.string.nav_item_calendar,
        Octicons.Calendar24,

        Routes.Dashboard.CALENDER,
        type = "link"
    )
    object Profile:BottomNavigationScreenItem(
        R.string.nav_item_profile,
        Octicons.Person24,

        Routes.Dashboard.PROFILE,
        type = "link"
    )
}

@Preview
@Composable
fun PreviewTuduBottomNavigation(){
    TuduTheme {
        TuduBottomNavigation(router = rememberNavController())
    }
}