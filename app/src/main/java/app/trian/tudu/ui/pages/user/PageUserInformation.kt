package app.trian.tudu.ui.pages.user

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.R
import app.trian.tudu.common.getTheme
import app.trian.tudu.domain.ThemeData
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.UserViewModel
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.accompanist.systemuicontroller.rememberSystemUiController
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
    router: NavHostController,
    theme:String
) {
    val userViewModel = hiltViewModel<UserViewModel>()
    val currentUser by userViewModel.currentUser.observeAsState()

    val systemUiController = rememberSystemUiController()
    val isSystemDark = isSystemInDarkTheme()
    val statusBar = MaterialTheme.colors.primary

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = statusBar,
            darkIcons = false
        )
    }
    LaunchedEffect(key1 = Unit, block = {
        userViewModel.getCurrentUser()
    })
    //todo
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 0.dp,
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
                    modifier = modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                   Image(
                       painter = rememberImagePainter(
                           data = currentUser?.photoUrl ?: stringResource(R.string.default_url_profile_image),
                           builder = {
                                transformations(CircleCropTransformation())
                           },
                           onExecute = { previous, current ->

                               true
                           }
                       ),
                       modifier=modifier.size(40.dp),
                       contentDescription = stringResource(R.string.content_description_image_profile)
                   )
                }
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(
                            top = 100.dp
                        )
                        .clip(
                            RoundedCornerShape(
                                topEnd = 20.dp,
                                topStart = 20.dp
                            )
                        )
                        .background(MaterialTheme.colors.background)

                ) {
                    Spacer(modifier = modifier.height(40.dp))
                    ItemProfileUser(
                        label = "Name",
                        text = currentUser?.displayName ?: stringResource(id = R.string.placeholder_unknown),
                        actionText = "EDIT"
                    )
                    ItemProfileUser(
                        label = "Email",
                        text = currentUser?.email ?: stringResource(id = R.string.placeholder_unknown),
                        actionText = ""
                    )

                }
            }
        }
    }
}

@Composable
fun ItemProfileUser(
    modifier: Modifier=Modifier,
    label:String="name",
    text:String="Name",
    actionText:String="EDIT",
    onAction:()->Unit= {}

){
    Column(
        modifier = modifier.fillMaxWidth(),

    ) {
        Spacer(modifier = modifier.height(16.dp))
        Text(
            text = label,
            modifier = modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = modifier.height(16.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text)
            Text(text = actionText)
        }
        Spacer(modifier = modifier.height(16.dp))
        Divider()
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
        PageUserInformation(
            router = rememberNavController(),
            theme = ThemeData.DEFAULT.value
        )
    }
}