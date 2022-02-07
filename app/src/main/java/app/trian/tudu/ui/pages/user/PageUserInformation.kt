package app.trian.tudu.ui.pages.user

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.R
import app.trian.tudu.ui.theme.TuduTheme
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import compose.icons.Octicons
import compose.icons.octicons.ArrowLeft24

/**
 * Page UserInformation
 * author Trian Damai
 * created_at 06/02/22 - 20.43
 * site https://trian.app
 */
@Composable
fun PageUserInformation(
    modifier: Modifier = Modifier,
    router: NavHostController
) {
    //todo
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        router.popBackStack()
                    }) {
                        Icon(
                            imageVector = Octicons.ArrowLeft24,
                            contentDescription = stringResource(R.string.content_description_home_button)
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.title_page_user_information)
                    )
                }
            )
        }
    ) {
        Column() {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.primary)
            ) {
                Column(

                ) {
                   Image(
                       painter = rememberImagePainter(
                           data = "https://via.placeholder.com/300",
                           builder = {
                                transformations(CircleCropTransformation())
                           },
                           onExecute = { previous, current ->

                               true
                           }
                       ),
                       modifier=modifier.size(40.dp),
                       contentDescription = ""
                   )
                }
            }
        }
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun PreviewPageUserInformation(){
    TuduTheme {
        PageUserInformation(router = rememberNavController())
    }
}