/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package app.trian.tudu.base.extensions

import app.trian.tudu.ApplicationState
import app.trian.tudu.base.listener.AppStateEventListener
import app.trian.tudu.base.listener.BottomSheetStateListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


//region route
fun ApplicationState.backPressedAndClose() {
    router.popBackStack(
        currentRoute,
        inclusive = true
    )
    exit()
}

fun ApplicationState.navigateUp() {
    router.navigateUp()
}

/**
 * Navigation into [routeName] as destination and keep the previous route
 *
 * @param routeName is a destination
 * @param args is arguments/parameter also support multiple type
 *
 * @throws IllegalArgumentException from navHostController
 */
fun ApplicationState.navigate(routeName: String, vararg args:String) {
    var buildRoute = routeName
    if (args.isNotEmpty()) {
        args.forEach {
            buildRoute = buildString {
                append(routeName)
                append("/")
                append(it)
            }
        }
    }
    this.router.navigate(buildRoute)
}

/**
 * Navigation into [routeName] as destination and keep the previous route
 * if same route exist on back stack, will be replace with [routeName]
 *
 * @param routeName is a destination
 * @param args is arguments/parameter also support multiple type
 *
 * @throws IllegalArgumentException from navHostController
 */
fun ApplicationState.navigateSingleTop(routeName: String, vararg args:String) {
    var buildRoute = routeName
    if (args.isNotEmpty()) {
        args.forEach {
           buildRoute = buildString {
               append(routeName)
               append("/")
               append(it)
           }
        }
    }
    this.router.navigate(buildRoute) {
        launchSingleTop = true
    }
}
fun ApplicationState.navigateSingleTop(routeName: String) {
    this.router.navigate(routeName) {
        launchSingleTop = true
    }
}

/**
 * Navigation into [routeName] as destination, and pop all backstack before last route
 * if same route exist on back stack, will be replace with [routeName]
 *
 * @param routeName is a destination
 * @param args is arguments/parameter also support multiple type
 *
 * @throws IllegalArgumentException from navHostController
 */
fun ApplicationState.navigateAndReplace(routeName: String, vararg args:String) {
    var buildRoute = routeName
    if (args.isNotEmpty()) {
        args.forEach {
            buildRoute = buildString {
                append(routeName)
                append("/")
                append(it)
            }
        }
    }
    this.router.navigate(buildRoute) {
        popUpTo(currentRoute)
        launchSingleTop = true
    }
}

/**
 * Navigation into [routeName] as destination, and pop all backstack from last route
 * if same route exist on back stack, will be replace with [routeName]
 *
 * @param routeName is a destination
 * @param args is arguments/parameter also support multiple type
 *
 *
 * @throws IllegalArgumentException from navHostController
 */
fun ApplicationState.navigateAndReplaceAll(routeName: String, vararg args:String) {
    var buildRoute = routeName
    if (args.isNotEmpty()) {
        args.forEach {
            buildRoute = buildString {
                append(routeName)
                append("/")
                append(it)
            }
        }
    }
    this.router.navigate(buildRoute) {
        popUpTo(currentRoute) {
            inclusive = true
        }
    }
}
fun ApplicationState.navigateAndReplaceAll(routeName: String) {
    this.router.navigate(routeName) {
        popUpTo(currentRoute) {
            inclusive = true
        }
    }
}


//end region

//region coroutine
fun ApplicationState.runSuspend(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = this.scope.launch(
    context, start, block
)

//end region


//region event
fun ApplicationState.addOnEventListener(listener: AppStateEventListener) =
    event.addOnEventListener(listener)

fun ApplicationState.addOnBottomSheetStateChangeListener(listener: BottomSheetStateListener) =
    event.addOnBottomSheetStateListener(listener)


fun ApplicationState.sendEvent(eventName: String) =
    event.sendEvent(eventName)

fun ApplicationState.exit() =
    event.sendEvent("EXIT")
//end region

fun ApplicationState.listenChanges() = this.router.addOnDestinationChangedListener { _, destination, _ ->
    currentRoute = destination.route.orEmpty()

}

fun ApplicationState.showBottomSheet(){
    runSuspend {
        bottomSheetState.show()
    }
}

fun ApplicationState.hideBottomSheet(){
    runSuspend {
        bottomSheetState.hide()
    }
}