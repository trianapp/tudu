package app.trian.tudu.feature.inputNote

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.trian.tudu.ApplicationState
import app.trian.tudu.R
import app.trian.tudu.base.BaseMainApp
import app.trian.tudu.base.UIWrapper
import app.trian.tudu.components.DialogConfirmation
import app.trian.tudu.feature.inputNote.InputNoteEvent.SetTaskNote

object InputNote {
    const val routeName = "InputNote"
    const val keyArgs = "taskId"
    fun routeName() = buildString {
        append(routeName)
        append("/")
        append("{$keyArgs}")
    }

    val args = listOf(navArgument(keyArgs) {
        type = NavType.StringType
    })
}

fun NavGraphBuilder.routeInputNote(
    state: ApplicationState,
) {
    composable(
        route = InputNote.routeName(),
        arguments = InputNote.args
    ) {
        ScreenInputNote(appState = state)
    }
}

@Composable
internal fun ScreenInputNote(
    appState: ApplicationState,
) = UIWrapper<InputNoteViewModel>(appState = appState) {
    val state by uiState.collectAsState()
    with(appState) {
        setupTopAppBar {
            TopAppBar(
                navigationIcon = {},
                title = {
                    Text(
                        text = state.taskName,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
        setupBottomAppBar {
            BottomAppBar(
                actions = {
                    IconButton(
                        onClick = {
                            dispatch(InputNoteEvent.CheckBackPressed)
                        }
                    ) {
                        Icon(
                            imageVector = Outlined.Close,
                            contentDescription = ""
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            dispatch(InputNoteEvent.SubmitNote)
                        },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            imageVector = Outlined.Save,
                            contentDescription = ""
                        )
                    }
                },
            )
        }
    }

    BackHandler {
        dispatch(InputNoteEvent.CheckBackPressed)
    }

    DialogConfirmation(
        show = state.showDialogBackConfirmation,
        message = stringResource(R.string.text_message_leave_page_confirmation),
        onConfirm = {
            commit {
                copy(
                    showDialogBackConfirmation = false,
                    isUpdateNote = false
                )
            }
        },
        onCancel = {
            commit { copy(showDialogBackConfirmation = false) }
        }
    )

    LaunchedEffect(key1 = this, block = {
        dispatch(InputNoteEvent.GetDetailTask)
    })
    LazyColumn(content = {
        item {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                value = state.taskNote,
                keyboardActions = KeyboardActions(
                    onDone = {},
                    onNext = {},
                    onPrevious = {}
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    autoCorrect = false,
                    imeAction = ImeAction.Next
                ),
                onValueChange = {
                    dispatch(SetTaskNote(it))
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.placeholder_input_note),
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.surfaceVariant

                    )
                },
                textStyle = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
        }
    })
}


@Preview
@Composable
fun PreviewScreenInputNote() {
    BaseMainApp {
        ScreenInputNote(it)
    }
}