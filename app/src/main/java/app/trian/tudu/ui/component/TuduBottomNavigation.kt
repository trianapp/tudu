package app.trian.tudu.ui.component

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

import androidx.navigation.compose.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import app.trian.tudu.R
import app.trian.tudu.common.Routes
import app.trian.tudu.ui.theme.TuduTheme
import compose.icons.Octicons
import compose.icons.octicons.Calendar16
import compose.icons.octicons.Home16
import compose.icons.octicons.Person16

@Composable
fun TuduBottomNavigation(
    router: NavHostController
){
    val navItems = listOf(
        BottomNavigationScreenItem.Home,
        BottomNavigationScreenItem.Calender,
        BottomNavigationScreenItem.Profile,
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        val navBackStackEntry by router.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        navItems.forEach {
            val selected = currentRoute == it.route
            BottomNavigationItem(
                label={
                      Text(text = stringResource(id = it.name))
                },
                selected = selected,
                onClick = {
                    if(!selected){
                        router.navigate(it.route)
                    }

                },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onPrimary,
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = "",
                        tint =if(selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    )
                }
            )
        }
    }
}

sealed class BottomNavigationScreenItem(
        var name:Int,
        var icon:ImageVector,
        var selectedIcon:Int,
        var route:String
    ){
    object Home:BottomNavigationScreenItem(
        R.string.nav_item_home,
        Octicons.Home16,
        1,
        Routes.Dashboard.HOME
    )
    object Calender:BottomNavigationScreenItem(
        R.string.nav_item_calendar,
        Octicons.Calendar16,
        1,
        Routes.Dashboard.CALENDER
    )
    object Profile:BottomNavigationScreenItem(
        R.string.nav_item_profile,
        Octicons.Person16,
        1,
        Routes.Dashboard.PROFILE
    )
}

@Preview
@Composable
fun PreviewTuduBottomNavigation(){
    TuduTheme {
        TuduBottomNavigation(router = rememberNavController())
    }
}