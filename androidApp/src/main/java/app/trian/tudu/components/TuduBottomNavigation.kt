package app.trian.tudu.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.R
import app.trian.tudu.ApplicationState
import app.trian.tudu.base.BaseMainApp
import app.trian.tudu.base.extensions.navigateSingleTop
import app.trian.tudu.feature.dashboard.calendar.Calendar
import app.trian.tudu.feature.dashboard.home.Home
import app.trian.tudu.feature.dashboard.profile.Profile


@Composable
fun TuduBottomNavigation(
    appState: ApplicationState
) {
    val navItems = listOf(
        BottomNavigationScreenItem.MenuHome,
        BottomNavigationScreenItem.MenuCalender,
        BottomNavigationScreenItem.MenuProfile,
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        tonalElevation = 1.dp,
        windowInsets = WindowInsets.navigationBars
    ) {
        navItems.forEach {
            val selected = appState.currentRoute == it.route

            NavigationBarItem(
                modifier = Modifier.clip(
                    RoundedCornerShape(
                        6.dp
                    )
                ),
                label = {
                    Text(
                        text = stringResource(id = it.name),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        )
                    )
                },
                selected = if (it.type == "button") false else selected,
                onClick = {
                    if (!selected) {
                        appState.navigateSingleTop(it.route)
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onSurface,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.surface
                ),
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.contentDescription,
                        tint = if (selected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onBackground
                    )
                }
            )

        }
    }

}

sealed class BottomNavigationScreenItem(
    var name: Int,
    var icon: ImageVector,
    var route: String = "",
    var type: String = "link",
    var contentDescription: String = ""
) {
    object MenuHome : BottomNavigationScreenItem(
        name = R.string.nav_item_home,
        icon = Icons.Outlined.Home,
        route = Home.routeName,
        type = "link",
        contentDescription = "Menu Home"
    )

    object MenuCalender : BottomNavigationScreenItem(
        name = R.string.nav_item_calendar,
        icon = Icons.Outlined.CalendarMonth,
        route = Calendar.routeName,
        type = "link",
        contentDescription = "Menu Calendar"
    )

    object MenuProfile : BottomNavigationScreenItem(
        name = R.string.nav_item_profile,
        icon = Icons.Outlined.Person,
        route = Profile.routeName,
        type = "link",
        contentDescription = "Menu My Profile"
    )
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun PreviewTuduBottomNavigation() {
    BaseMainApp {
        TuduBottomNavigation(it)
    }
}