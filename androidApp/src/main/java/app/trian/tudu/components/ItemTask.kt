package app.trian.tudu.components

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.SecureFlagPolicy.Inherit
import app.trian.tudu.R
import app.trian.tudu.base.BaseMainApp
import app.trian.tudu.base.extensions.formatDate
import app.trian.tudu.data.model.TaskModel
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import java.time.LocalDate
import java.time.OffsetDateTime

/**
 * TaskItem
 * author Trian Damai
 * created_at 29/01/22 - 14.39
 * site https://trian.app
 */

@ExperimentalFoundationApi
@Composable
fun ItemTaskRow(
    modifier: Modifier = Modifier,
    taskName: String = "",
    taskNote: String = "",
    taskDone: Boolean = false,
    taskDueDate: String = "",
    taskDueTime: String = "",
    categories: List<String> = listOf(),
    onDetail: () -> Unit = {},
    onDelete: () -> Unit = {},
    onDone: () -> Unit = {}
) {

    var showDropDown by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 4.dp,
            )
    ) {
        //

        Row(
            modifier = modifier
                .align(Companion.Center)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.small)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant
                )
                .combinedClickable(
                    onClick = {
                        onDetail()
                    },
                    onLongClick = {
                        showDropDown = true
                    }
                )
                .padding(
                    top = 16.dp,
                    bottom = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .defaultMinSize(
                        minHeight = 120.dp
                    ),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = taskName,
                        maxLines = 1,
                        modifier = modifier.fillMaxWidth(fraction = 0.8f),
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textDecoration = if (taskDone) TextDecoration.LineThrough else TextDecoration.None
                        )
                    )
                    Text(
                        text = taskNote,
                        maxLines = 2,
                        modifier = modifier.fillMaxWidth(fraction = 0.8f),
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    )
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        mainAxisSize = SizeMode.Wrap,
                        mainAxisAlignment = FlowMainAxisAlignment.Start,
                        crossAxisSpacing = 6.dp,
                        crossAxisAlignment = FlowCrossAxisAlignment.Start
                    ) {
                        categories.forEach {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .border(
                                        border = BorderStroke(
                                            width = 1.dp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        ),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .padding(
                                        top = 1.dp,
                                        bottom = 1.dp,
                                        start = 8.dp,
                                        end = 8.dp
                                    )
                            ) {
                                Text(
                                    text = "# $it",
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Outlined.CalendarMonth,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = taskDueDate,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Light,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )

                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Row {
                        Icon(
                            imageVector = Outlined.Timer, contentDescription = "",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = taskDueTime,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Light,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        }

        IconButton(
            onClick = {
                showDropDown = true
            },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            DropdownMenu(
                expanded = showDropDown,
                onDismissRequest = {
                    showDropDown = false
                },
                properties = PopupProperties(
                    excludeFromSystemGesture = false,
                    clippingEnabled = false,
                    securePolicy = Inherit
                ),
                offset = DpOffset.Zero
            ) {
                DropdownMenuItem(
                    onClick = {
                        onDelete()
                        showDropDown = false
                    },
                    text = {
                        Text(
                            text = stringResource(id = R.string.btn_delete),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    leadingIcon = {
                        Icon(imageVector = Outlined.Delete, contentDescription = "")
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
            Icon(imageVector = Outlined.MoreVert, contentDescription = "")
        }
        Checkbox(
            checked = taskDone,
            modifier = Modifier.align(Alignment.BottomEnd),
            onCheckedChange = {
                onDone()
            }
        )
    }
}


@ExperimentalFoundationApi
@SuppressLint("NewApi")
@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewItemTaskRow() {
    val task = listOf(
        TaskModel(
            taskId = "Ini task pertama",
            taskName = "task pertama",
            taskDueDate = LocalDate.now().formatDate("dd MMM yy"),
            taskDone = false,
            taskNote = "When I first tried to write a Compose integration UI test" +
                    " (that was when Jetpack Compose became stable, in July 2021), ",
            taskReminder = false,
            createdAt = OffsetDateTime.now().toString(),
            updatedAt = OffsetDateTime.now().toString()
        ),
        TaskModel(
            taskId = "Ini Task Kedua",
            taskName = "task pertama",
            taskDueDate = LocalDate.now().formatDate("dd MMM yy"),
            taskDone = false,
            taskNote = ". Hilt testing guide examples use the old View system so" +
                    " it can serve as a guidepost,",
            taskReminder = false,
            createdAt = LocalDate.now().toString(),
            updatedAt = LocalDate.now().toString()
        )
    )
    BaseMainApp {
        LazyColumn(
            contentPadding = PaddingValues(
                horizontal = 16.dp
            ),
            content = {
                items(task) {
                    ItemTaskRow(
                        taskDone = it.taskDone,
                        taskName = it.taskName,
                        taskNote = it.taskNote,
                        taskDueDate = it.taskDueDate
                    )
                }
            })
    }
}
