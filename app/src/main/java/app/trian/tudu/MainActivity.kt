package app.trian.tudu

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType

import androidx.navigation.navArgument
import app.trian.tudu.common.Routes
import app.trian.tudu.common.getTheme
import app.trian.tudu.data.local.AppSetting
import app.trian.tudu.domain.ThemeData
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
import app.trian.tudu.viewmodel.UserViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
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

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberAnimatedNavController()
            val systemUiController = rememberSystemUiController()

            // A surface container using the 'background' color from the theme
            val uiColor = MaterialTheme.colors.background

            val userViewModel = hiltViewModel<UserViewModel>()
            val currentSetting by userViewModel.appSetting.observeAsState(initial = AppSetting())
            val useDark by userViewModel.isDarkTheme.observeAsState(initial = ThemeData.DEFAULT)
            val dark = isSystemInDarkTheme()
            LaunchedEffect(key1 = Unit, block = {
                //https://github.com/google/accompanist/issues/918
                systemUiController.setSystemBarsColor(
                    color = uiColor,
                    darkIcons = when(useDark){
                        ThemeData.DEFAULT -> !dark
                        ThemeData.DARK -> false
                        ThemeData.LIGHT -> true
                    }
                )
                userViewModel.getCurrentSetting()

            })

            TuduTheme(
                darkTheme = when(currentSetting.theme.getTheme()){
                    ThemeData.DEFAULT -> isSystemInDarkTheme()
                    ThemeData.DARK -> true
                    ThemeData.LIGHT -> false
                }
            ) {


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AnimatedNavHost(
                        navController = navHostController,
                        startDestination = Routes.SPLASH
                    ){
                        composable(
                            Routes.SPLASH,
                            enterTransition = {
                                fadeIn(animationSpec = tween(400))
                            },
                            exitTransition = {

                                fadeOut(animationSpec = tween(400))

                            },
                        ){
                            PagesSplash(
                                router=navHostController,
                                theme = currentSetting.theme
                            )
                        }
                        composable(Routes.ONBOARD){

                            PagesOnboard(
                                router=navHostController,
                                theme = currentSetting.theme
                            )
                        }
                        composable(Routes.LOGIN){
                            PageLogin(
                                router=navHostController,
                                theme = currentSetting.theme
                            )
                        }
                        composable(Routes.REGISTER){

                            PagesRegister(
                                router=navHostController,
                                theme = currentSetting.theme
                            )
                        }
                        composable(Routes.CHANGE_PASSWORD){

                            PageChangePassword(
                                router = navHostController,
                                theme=currentSetting.theme
                            )
                        }
                        composable(Routes.RESET_PASSWORD){

                            PageResetPassword(
                                router = navHostController,
                                theme = currentSetting.theme
                            )
                        }
                        composable(Routes.SETTING){
                            PageSetting(
                                router = navHostController,
                                theme=currentSetting.theme
                            )
                        }
                        navigation(route=Routes.DASHBOARD, startDestination = Routes.Dashboard.HOME){
                            composable(
                                route=Routes.Dashboard.HOME,
                                enterTransition = {
                                    fadeIn(animationSpec = tween(700))
                                },
                                exitTransition = {

                                    fadeOut(animationSpec = tween(700))

                                },
                            ){
                                PageHome(
                                    router=navHostController,
                                    theme = currentSetting.theme,
                                    onChangeTheme = {
                                        userViewModel.updateCurrentSetting(currentSetting.apply { theme =it })
                                        restartActivity()
                                    },
                                    restartActivity = ::logout
                                )
                            }
                            composable(
                                route=Routes.Dashboard.CALENDER,
                                enterTransition = {
                                    fadeIn(animationSpec = tween(700))
                                },
                                exitTransition = {

                                    fadeOut(animationSpec = tween(700))

                                },
                            ){

                                PageCalender(
                                    router=navHostController,
                                    theme = currentSetting.theme,
                                    onChangeTheme = {
                                        userViewModel.updateCurrentSetting(currentSetting.apply { theme =it })
                                        restartActivity()
                                    },
                                    restartActivity = ::logout
                                )

                            }
                            composable(
                                route=Routes.Dashboard.PROFILE,
                                enterTransition = {
                                    fadeIn(animationSpec = tween(700))
                                },
                                exitTransition = {

                                    fadeOut(animationSpec = tween(700))

                                },
                            ){

                                PageProfile(
                                    router=navHostController,
                                    theme = currentSetting.theme,
                                    onChangeTheme = {
                                        userViewModel.updateCurrentSetting(currentSetting.apply { theme =it })
                                        restartActivity()
                                    },
                                    restartActivity = ::logout
                                )

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

                            PageDetailTask(
                                router = navHostController,
                                theme = currentSetting.theme
                            )
                        }
                        composable(
                            "${Routes.ADD_NOTE}/{taskId}",
                            arguments = listOf(
                                navArgument("taskId"){
                                    type = NavType.StringType
                                }
                            )
                        ){

                            PageInputNote(
                                router = navHostController,
                                theme = currentSetting.theme
                            )
                        }
                        composable(Routes.SEARCH_TASK){

                            PageSearchTask(router = navHostController)
                        }
                        composable(Routes.CATEGORY){

                            PagesCategoryManagement(
                                 router=navHostController,
                                theme = currentSetting.theme
                            )
                        }
                        composable(Routes.PAGE_USER_INFORMATION){

                            PageUserInformation(
                                router = navHostController,
                                theme = currentSetting.theme
                            )
                        }
                    }
                }
            }
        }

    }

    private fun restartActivity(){
      runOnUiThread {
          this.recreate()
      }
    }
    private fun logout(){
        Intent(this,MainActivity::class.java).apply {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }.also {
            startActivity(it)
            finish()
        }
    }

}
