package app.trian.tudu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.trian.tudu.common.Routes
import app.trian.tudu.data.repository.design.UserRepository
import app.trian.tudu.ui.pages.auth.*
import app.trian.tudu.ui.pages.category.PagesCategoryManagement
import app.trian.tudu.ui.pages.dashbboard.PageCalender
import app.trian.tudu.ui.pages.dashbboard.PageHome
import app.trian.tudu.ui.pages.dashbboard.PageProfile
import app.trian.tudu.ui.pages.setting.PageSetting
import app.trian.tudu.ui.pages.task.PageDetailTask
import app.trian.tudu.ui.pages.task.PageInputNote
import app.trian.tudu.ui.pages.task.PageSearchTask
import app.trian.tudu.ui.pages.user.PageUserInformation
import app.trian.tudu.ui.theme.TuduTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            val systemUiController = rememberSystemUiController()



            TuduTheme {
                // A surface container using the 'background' color from the theme
                val uiColor = MaterialTheme.colors.background
                val primaryColor = MaterialTheme.colors.primary
                val useDark = isSystemInDarkTheme()
                LaunchedEffect(key1 = Unit, block = {
                    systemUiController.setSystemBarsColor(
                        color = uiColor,
                        darkIcons = !useDark
                    )

                })

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(
                        navController = navHostController,
                        startDestination = Routes.SPLASH
                    ){
                        composable(Routes.SPLASH){
                            systemUiController.setSystemBarsColor(
                                color = uiColor,
                                darkIcons = !useDark
                            )
                            PagesSplash(
                                router=navHostController
                            )
                        }
                        composable(Routes.ONBOARD){
                            systemUiController.setSystemBarsColor(
                                color = uiColor,
                                darkIcons = !useDark
                            )
                            PagesOnboard(
                                router=navHostController,
                                )
                        }
                        composable(Routes.LOGIN){
                            systemUiController.setSystemBarsColor(
                                color = uiColor,
                                darkIcons = !useDark
                            )
                            PageLogin(
                                router=navHostController
                            )
                        }
                        composable(Routes.REGISTER){
                            systemUiController.setSystemBarsColor(
                                color = uiColor,
                                darkIcons = !useDark
                            )
                            PagesRegister(
                                router=navHostController
                            )
                        }
                        composable(Routes.CHANGE_PASSWORD){
                            systemUiController.setSystemBarsColor(
                                color = uiColor,
                                darkIcons = !useDark
                            )
                            PageChangePassword(
                                router = navHostController
                            )
                        }
                        composable(Routes.RESET_PASSWORD){
                            systemUiController.setSystemBarsColor(
                                color = uiColor,
                                darkIcons = !useDark
                            )
                            PageResetPassword(router = navHostController)
                        }
                        composable(Routes.SETTING){
                            systemUiController.setSystemBarsColor(
                                color = uiColor,
                                darkIcons = !useDark
                            )
                            PageSetting(router = navHostController)
                        }
                        navigation(route=Routes.DASHBOARD, startDestination = Routes.Dashboard.HOME){
                            composable(route=Routes.Dashboard.HOME){
                                systemUiController.setSystemBarsColor(
                                    color = uiColor,
                                    darkIcons = !useDark
                                )
                                PageHome(router=navHostController)
                            }
                            composable(route=Routes.Dashboard.CALENDER){
                                systemUiController.setSystemBarsColor(
                                    color = primaryColor,
                                    darkIcons = useDark
                                )
                                PageCalender(router=navHostController)

                            }
                            composable(route=Routes.Dashboard.PROFILE){
                                systemUiController.setSystemBarsColor(
                                    color = primaryColor,
                                    darkIcons = useDark
                                )
                                PageProfile(router=navHostController)

                            }
                        }
                        composable(
                            "${Routes.DETAIL_TASK}/{taskId}",
                            arguments = listOf(
                                navArgument("taskId"){
                                    type = NavType.StringType
                                }
                            )
                        ){
                            systemUiController.setSystemBarsColor(
                                color = uiColor,
                                darkIcons = !useDark
                            )
                            PageDetailTask(router = navHostController)
                        }
                        composable(
                            "${Routes.ADD_NOTE}/{taskId}",
                            arguments = listOf(
                                navArgument("taskId"){
                                    type = NavType.StringType
                                }
                            )
                        ){
                            systemUiController.setSystemBarsColor(
                                color = uiColor,
                                darkIcons = !useDark
                            )
                            PageInputNote(router = navHostController)
                        }
                        composable(Routes.SEARCH_TASK){
                            systemUiController.setSystemBarsColor(
                                color = uiColor,
                                darkIcons = !useDark
                            )
                            PageSearchTask(router = navHostController)
                        }
                        composable(Routes.CATEGORY){
                            systemUiController.setSystemBarsColor(
                                color = uiColor,
                                darkIcons = !useDark
                            )
                            PagesCategoryManagement(
                                 router=navHostController
                            )
                        }
                        composable(Routes.PAGE_USER_INFORMATION){
                            systemUiController.setSystemBarsColor(
                                color = primaryColor,
                                darkIcons = !useDark
                            )
                            PageUserInformation(router = navHostController)
                        }
                    }
                }
            }
        }

    }


}
