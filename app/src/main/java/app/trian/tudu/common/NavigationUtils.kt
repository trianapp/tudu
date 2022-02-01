package app.trian.tudu.common

import androidx.navigation.NavHostController

/**
 * Navigation Utils
 * author Trian Damai
 * created_at 01/02/22 - 23.29
 * site https://trian.app
 */

fun NavHostController.signOut(){
    navigate(Routes.LOGIN){
        popUpTo(Routes.SPLASH){
            inclusive=true
            saveState=false
        }
        popUpTo(Routes.ONBOARD){
            inclusive=true
            saveState=false
        }
        popUpTo(Routes.REGISTER){
            inclusive=true
            saveState=false
        }
        popUpTo(Routes.DASHBOARD){
            inclusive=true
            saveState=false
        }
        popUpTo(Routes.Dashboard.CALENDER){
            inclusive=true
            saveState=false
        }
        popUpTo(Routes.Dashboard.HOME){
            inclusive=true
            saveState=false
        }
        popUpTo(Routes.Dashboard.PROFILE){
            inclusive=true
            saveState=false
        }
        popUpTo(Routes.CHANGE_PASSWORD){
            inclusive=true
            saveState=false
        }
        popUpTo(Routes.SETTING){
            inclusive=true
            saveState=false
        }
        popUpTo(Routes.CATEGORY){
            inclusive=true
            saveState=false
        }
        launchSingleTop=true
        restoreState = false
    }
}

fun NavHostController.signInInSuccess(){
    navigate(Routes.DASHBOARD){
        popUpTo(Routes.SPLASH){
            inclusive=true
            saveState=false
        }
        popUpTo(Routes.ONBOARD){
            inclusive=true
            saveState=false
        }
        popUpTo(Routes.REGISTER){
            inclusive=true
            saveState=false
        }
        launchSingleTop=true
        restoreState=false
    }
}