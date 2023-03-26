/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package app.trian.tudu.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.ApplicationState
import app.trian.tudu.rememberApplicationState
import app.trian.tudu.theme.MyApplicationTheme

@Composable
fun BaseMainApp(
    appState: ApplicationState = rememberApplicationState(),
    topAppBar: @Composable (ApplicationState) -> Unit = {
        if (it.showTopAppBar) {
            it.topAppBar.invoke()
        }
    },
    bottomBar: @Composable (ApplicationState) -> Unit = {
        if (it.showBottomAppBar) {
            it.bottomAppBar.invoke()
        }
    },
    snackbarBar: @Composable (ApplicationState) -> Unit = { state ->
        SnackbarHost(
            hostState = state.snackbarHostState,
            snackbar = { state.snackbar.invoke(it) })
    },
    bottomSheet: @Composable (ApplicationState) -> Unit = { it.bottomSheet.invoke() },
    content: @Composable (appState: ApplicationState) -> Unit = { }
) {
    MyApplicationTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface
        ) {
            ModalBottomSheetLayout(
                sheetContent = {
                    Column(
                        Modifier
                            .defaultMinSize(
                                minHeight = 50.dp
                            )
                            .wrapContentHeight()
                    ) {
                        bottomSheet(appState)
                    }
                },
                sheetState = appState.bottomSheetState,
                sheetBackgroundColor = MaterialTheme.colorScheme.surface,
                sheetShape = RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp
                )
            ) {
                Scaffold(
                    topBar = {
                        topAppBar(appState)
                    },
                    bottomBar = {
                        bottomBar(appState)
                    },
                    snackbarHost = {
                        snackbarBar(appState)
                    },
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    contentWindowInsets = WindowInsets.ime
                ) {
                    Column(
                        modifier = Modifier.padding(it)
                    ) {
                        content(appState)
                    }
                }
            }

        }
    }
}

@Preview
@Composable
fun PreviewBaseMainApp() {
    BaseMainApp()
}