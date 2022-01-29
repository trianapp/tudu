package app.trian.tudu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import app.trian.tudu.ui.pages.task.PageDetailTask
import app.trian.tudu.ui.theme.TuduTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            TuduTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
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
                                router=navHostController
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
                        navigation(route=Routes.DASHBOARD, startDestination = Routes.Dashboard.HOME){
                            composable(route=Routes.Dashboard.HOME){
                                PageHome(router=navHostController)
                            }
                            composable(route=Routes.Dashboard.CALENDER){
                                PageCalender(navHostController=navHostController)

                            }
                            composable(route=Routes.Dashboard.PROFILE){
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
