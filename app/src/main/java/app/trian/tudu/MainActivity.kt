package app.trian.tudu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import app.trian.tudu.ui.pages.auth.*
import app.trian.tudu.ui.pages.category.PagesCategoryManagement
import app.trian.tudu.ui.pages.dashbboard.PageCalender
import app.trian.tudu.ui.pages.dashbboard.PageHome
import app.trian.tudu.ui.pages.dashbboard.PageProfile
import app.trian.tudu.ui.pages.setting.PageSetting
import app.trian.tudu.ui.pages.task.PageDetailTask
import app.trian.tudu.ui.pages.task.PageInputNote
import app.trian.tudu.ui.theme.TuduTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

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
                LaunchedEffect(key1 = Unit, block = {
                    systemUiController.setSystemBarsColor(
                        color = uiColor,
                        darkIcons = true
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
                            PagesSplash(
                                router=navHostController
                            )
                        }
                        composable(Routes.ONBOARD){
                            PagesOnboard(
                                router=navHostController,
                                )
                        }
                        composable(Routes.LOGIN){
                            PageLogin(
                                router=navHostController
                            )
                        }
                        composable(Routes.REGISTER){
                            PagesRegister(
                                router=navHostController
                            )
                        }
                        composable(Routes.CHANGE_PASSWORD){
                            PageChangePassword(
                                router = navHostController
                            )
                        }
                        composable(Routes.SETTING){
                            PageSetting(router = navHostController)
                        }
                        navigation(route=Routes.DASHBOARD, startDestination = Routes.Dashboard.HOME){
                            composable(route=Routes.Dashboard.HOME){
                                systemUiController.setSystemBarsColor(
                                    color = uiColor,
                                    darkIcons = true
                                )
                                PageHome(router=navHostController)
                            }
                            composable(route=Routes.Dashboard.CALENDER){
                                systemUiController.setSystemBarsColor(
                                    color = uiColor,
                                    darkIcons = true
                                )
                                PageCalender(router=navHostController)

                            }
                            composable(route=Routes.Dashboard.PROFILE){
                                systemUiController.setSystemBarsColor(
                                    color = primaryColor,
                                    darkIcons = false
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
                            PageInputNote(router = navHostController)
                        }
                        composable(Routes.CATEGORY){
                            PagesCategoryManagement(
                                 router=navHostController
                            )
                        }
                    }
                }
            }
        }
    }
}
