package app.trian.tudu.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import app.trian.tudu.R
import app.trian.tudu.base.BaseMainApp
import app.trian.tudu.data.model.CategoryModel
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode

/**
 * Dialog Form Category
 * author Trian Damai
 * created_at 29/01/22 - 20.00
 * site https://trian.app
 */

@Composable
fun DialogPickCategory(
    show: Boolean = false,
    categories: List<CategoryModel> = listOf(),
    currentSelectedCategory: List<CategoryModel> = listOf(),
    onHide: () -> Unit = {},
    onSubmit: (categories: List<CategoryModel>) -> Unit = {}
) {
    if (show) {
        Dialog(
            onDismissRequest = {
                onHide()
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            ) {
                ScreenDialogPickCategory(
                    categories = categories,
                    currentSelectedCategory = currentSelectedCategory,
                    onSubmit = onSubmit,
                    onHide = onHide,
                )
            }
        }
    }
}


@Composable
fun ScreenDialogPickCategory(
    currentSelectedCategory: List<CategoryModel> = listOf(),
    categories: List<CategoryModel> = listOf(),
    onHide: () -> Unit = {},
    onSubmit: (categories: List<CategoryModel>) -> Unit = {}
) {
    var selectedCategory = remember {
        mutableStateListOf<CategoryModel>()
    }
    LaunchedEffect(key1 = selectedCategory, block = {
        selectedCategory.addAll(currentSelectedCategory)
    })
    Box(
        modifier = Modifier
            .clip(
                MaterialTheme.shapes.medium
            )
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Column {
            Text(
                text = stringResource(R.string.title_pick_category),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                mainAxisSize = SizeMode.Wrap,
                mainAxisAlignment = FlowMainAxisAlignment.Start,
                crossAxisSpacing = 6.dp,
                crossAxisAlignment = FlowCrossAxisAlignment.Start
            ) {
                categories.forEachIndexed { _, categoryModel ->
                    val selected = categoryModel in selectedCategory
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .border(
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = if (selected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clip(
                                RoundedCornerShape(20.dp)
                            )
                            .background(
                                color = if (categoryModel in selectedCategory) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.surface
                            )
                            .clickable {
                                if (categoryModel.categoryId == "all") {
                                    selectedCategory.clear()
                                    if (!selected) {
                                        selectedCategory.addAll(categories)
                                    }
                                } else {
                                    if (selected) {
                                        selectedCategory.remove(categoryModel)
                                    } else {
                                        selectedCategory.add(categoryModel)
                                    }
                                }
                            }
                            .padding(
                                top = 1.dp,
                                bottom = 1.dp,
                                start = 8.dp,
                                end = 8.dp
                            )
                    ) {
                        Text(
                            text = categoryModel.categoryName,
                            style = MaterialTheme.typography.labelMedium,
                            color = if (categoryModel in selectedCategory) MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = {
                    onHide()
                }) {
                    Text(
                        text = stringResource(R.string.btn_cancel),
                        style = MaterialTheme.typography.displayMedium.copy(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                            fontSize = 18.sp
                        )
                    )
                }
                TextButton(onClick = { onSubmit(selectedCategory) }) {
                    Text(
                        text = stringResource(R.string.btn_save),
                        style = MaterialTheme.typography.displayMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 18.sp
                        )
                    )
                }
            }
        }
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun PreviewScreenPickCategory() {
    BaseMainApp {
        ScreenDialogPickCategory(
            categories = listOf(
                CategoryModel(
                    categoryName = "Jajan"
                ),
                CategoryModel(
                    categoryName = "Kerja"
                ),
                CategoryModel(
                    categoryName = "Kantor"
                )
            ),

            onSubmit = {}
        )
    }
}