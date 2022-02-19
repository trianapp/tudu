package app.trian.tudu.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.R
import app.trian.tudu.common.getLogo
import app.trian.tudu.data.local.Category
import app.trian.tudu.ui.component.tab.TabBarHome
import app.trian.tudu.ui.theme.HexToJetpackColor
import app.trian.tudu.ui.theme.TuduTheme
import compose.icons.Octicons
import compose.icons.octicons.ArrowLeft16
import compose.icons.octicons.ArrowLeft24
import compose.icons.octicons.Home16

/**
 * App bar home
 * author Trian Damai
 * created_at 29/01/22 - 19.01
 * site https://trian.app
 */
@Composable
fun AppbarHome(
    modifier: Modifier=Modifier,
    dataCategory:List<Category> = listOf(),
    onOptionMenuSelected:(menu:Int)->Unit={},
    onSelectCategory:(category:Category)->Unit={}
) {
    val isDark = isSystemInDarkTheme()
    Column {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.title_appbar_home),
                    style=MaterialTheme.typography.h5
                )
            },
            backgroundColor = MaterialTheme.colors.background,
            navigationIcon = {
                Image(
                    modifier = modifier.size(40.dp),
                    painter = painterResource(id = R.drawable.logo) ,
                    contentDescription = "Logo Tudu"
                )
            },
            elevation = 0.dp
        )
        TabBarHome(
            tabData = dataCategory,
            onOptionMenuClicked=onOptionMenuSelected,
            onSelect =onSelectCategory
        )
    }

}
@Composable
fun AppbarAuth(
    modifier: Modifier=Modifier,
    onBackPressed:()->Unit={}
){
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        navigationIcon = {
            IconToggleButton(checked = false, onCheckedChange = {
                onBackPressed()
            }) {
                Icon(
                    imageVector = Octicons.ArrowLeft16,
                    contentDescription = ""
                )
            }
        },
        title = {}
    )
}
@Composable
fun AppbarBasic(
    title:String,
    onBackPressed: () -> Unit={}
){
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        navigationIcon = {
            IconToggleButton(checked = false, onCheckedChange = {onBackPressed()}) {
                Icon(
                    imageVector = Octicons.ArrowLeft24,
                    contentDescription = ""
                )
            }
        },
        title = {
            Text(
                text = title,
                style =MaterialTheme.typography.h5
            )
        }
    )
}
@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewAppbarHome(){
    TuduTheme {
           AppbarHome(dataCategory = listOf(
               Category(
                   name = "All",
                   created_at = 0,
                   updated_at = 0,
                   color = HexToJetpackColor.Blue,
               ),
               Category(
                   name = "Wishlist",
                   created_at = 0,
                   updated_at = 0,
                   color = HexToJetpackColor.Blue,
               ),
               Category(
                   name = "Tugas",
                   created_at = 0,
                   updated_at = 0,
                   color = HexToJetpackColor.Blue,
               ),
               Category(
                   name = "Kerjaan",
                   created_at = 0,
                   updated_at = 0,
                   color = HexToJetpackColor.Blue,
               ),
               Category(
                   name = "Cobaan",
                   created_at = 0,
                   updated_at = 0,
                   color = HexToJetpackColor.Blue,
               )
           ))
       }

}

@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewAppbarBasic(){
    TuduTheme {
        AppbarBasic(title = "Change password")
    }
}