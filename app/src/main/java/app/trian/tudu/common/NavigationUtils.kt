package app.trian.tudu.common

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

/**
 * Navigation Utils
 * author Trian Damai
 * created_at 01/02/22 - 23.29
 * site https://trian.app
 */

fun NavHostController.signOut(){
    val nav =this
    navigate(Routes.LOGIN){
        popUpTo(Routes.Dashboard.HOME){
            inclusive=true
        }
    }
}

fun NavHostController.signInInSuccess(){
    navigate(Routes.DASHBOARD){
       popUpTo(Routes.LOGIN){
           inclusive=true
       }
    }
}

fun NavHostController.signInInSuccessOnboard(){
    navigate(Routes.DASHBOARD){
        popUpTo(Routes.ONBOARD){
            inclusive=true
        }
    }
}