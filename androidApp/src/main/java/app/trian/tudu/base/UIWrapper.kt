/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package app.trian.tudu.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import app.trian.tudu.ApplicationState
@Composable
inline fun <reified VM : BaseViewModel<*, *>> UIWrapper(
    appState: ApplicationState,
    parent: String? = null,
    content: @Composable VM.() -> Unit = {}
) {
    val vm = (if (parent.isNullOrEmpty()) {
        hiltViewModel()
    } else {
        val parentEntry = remember(appState.router.currentBackStackEntry) {
            appState
                .router
                .getBackStackEntry(parent)
        }
        hiltViewModel<VM>(parentEntry)
    }).also { it.setAppState(appState) }
    content(vm)
}
