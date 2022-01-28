package app.trian.tudu.ui.component

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.material3.*

import androidx.navigation.compose.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController

@Composable
fun TuduBottomNavigation(
    navHostController: NavHostController
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
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        navItems.forEach {
            BottomNavigationItem(
                selected = currentRoute == it.route,
                onClick = { /*TODO*/ },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onPrimary,
                icon = {}
            )
        }
    }
}

sealed class BottomNavigationScreenItem(
        var name:Int,
        var icon:Int,
        var selectedIcon:Int,
        var route:String
    ){
    object Home:BottomNavigationScreenItem(1,1,1,"")
    object Calender:BottomNavigationScreenItem(1,1,1,"")
    object Profile:BottomNavigationScreenItem(1,1,1,"")
}