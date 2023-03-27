package app.trian.tudu

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue.Hidden
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.base.EventListener
import app.trian.tudu.base.extensions.runSuspend
import kotlinx.coroutines.CoroutineScope


class CreateContent(
    private val content: @Composable () -> Unit
) {
    @Composable
    fun invoke() {
        content.invoke()
    }
}

class CreateSnackbarContent(
    private val content: @Composable (SnackbarData) -> Unit
) {
    @Composable
    fun invoke(snackbarData: SnackbarData) {
        content.invoke(snackbarData)
    }
}

@OptIn(ExperimentalMaterialApi::class)
class ApplicationState internal constructor(
    val router: NavHostController,
    val bottomSheetState: ModalBottomSheetState,
    val scope: CoroutineScope,
    val event: EventListener,
    val context: Context
) {


    var currentRoute by mutableStateOf("")

    var snackbarHostState by mutableStateOf(
        SnackbarHostState()
    )

    internal var showBottomAppBar by mutableStateOf(false)
    internal var bottomAppBar by mutableStateOf(CreateContent {})

    internal var showTopAppBar by mutableStateOf(false)
    internal var topAppBar by mutableStateOf(CreateContent {})

    internal var snackbar by mutableStateOf(CreateSnackbarContent {
        Snackbar(snackbarData = it)
    })
    internal var bottomSheet by mutableStateOf(CreateContent {})

    @Composable
    fun setupBottomAppBar(
        content: @Composable () -> Unit = {}
    ) {
        LaunchedEffect(key1 = this, block = {
            bottomAppBar = CreateContent(content)
            if (!showBottomAppBar) {
                showBottomAppBar = true
            }
        })
    }

    @Composable
    fun setupTopAppBar(
        content: @Composable () -> Unit = {}
    ) {
        LaunchedEffect(key1 = this, block = {
            topAppBar = CreateContent(content)
            if (!showTopAppBar) {
                showTopAppBar = true
            }
        })

    }


    fun setupBottomSheet(content: @Composable () -> Unit = {}) {
        bottomSheet = CreateContent(content)
    }

    fun setupSnackbar(content: @Composable (SnackbarData) -> Unit = {}) {
        snackbar = CreateSnackbarContent(content)
    }

    fun setupDefaultSnackbar() {
        snackbar = CreateSnackbarContent {
            Snackbar(it)
        }
    }

    @Composable
    fun hideBottomAppBar() {
        LaunchedEffect(key1 = this, block = {
            if (showBottomAppBar) {
                showBottomAppBar = false
            }
        })
    }

    @Composable
    fun hideTopAppBar() {
        LaunchedEffect(key1 = this, block = {
            if (showTopAppBar) {
                showTopAppBar = false
            }
        })

    }

    fun showSnackbar(message: String) {
        runSuspend {
            snackbarHostState.showSnackbar(message)
        }
    }

    fun showSnackbar(message: Int) {
        runSuspend {
            snackbarHostState.showSnackbar(context.getString(message))
        }
    }

    fun showSnackbar(message: Int,vararg params: String) {
        runSuspend {
            snackbarHostState.showSnackbar(context.getString(message, *params))
        }
    }

    fun showSnackbar(message: String, duration: SnackbarDuration) {
        runSuspend {
            snackbarHostState.showSnackbar(message, duration = duration)
        }
    }

    fun showSnackbar(message: String, actionLabel: String, onAction: () -> Unit = {}) {
        runSuspend {
            when (snackbarHostState.showSnackbar(message, actionLabel = actionLabel, withDismissAction = true)) {
                SnackbarResult.Dismissed -> Unit
                SnackbarResult.ActionPerformed -> onAction()
            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberApplicationState(
    router: NavHostController = rememberNavController(),
    scope: CoroutineScope = rememberCoroutineScope(),
    event: EventListener = EventListener(),
    context: Context = LocalContext.current
): ApplicationState {
    val state = rememberModalBottomSheetState(
        initialValue = Hidden,
        confirmValueChange={
            event.changeBottomSheetState(it)
            true
        }
    )
    return remember {
        ApplicationState(
            router,
            state,
            scope,
            event,
            context
        )
    }
}