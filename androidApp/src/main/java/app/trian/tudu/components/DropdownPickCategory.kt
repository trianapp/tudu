package app.trian.tudu.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlusOne
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.SecureFlagPolicy
import app.trian.tudu.R
import app.trian.tudu.base.BaseMainApp
import category.Category
import java.time.OffsetDateTime

/**
 * Dropdown
 * author Trian Damai
 * created_at 31/01/22 - 20.59
 * site https://trian.app
 */
@SuppressLint("NewApi")
@Composable
fun DropdownPickCategory(
    modifier: Modifier =Modifier,
    show:Boolean,
    listCategory:List<Category>,
    onPick:(category:Category)->Unit={},
    onHide:()->Unit={},
    onAddCategory:()->Unit={},
    buttonAddCategory:@Composable ()->Unit={
        DropdownMenuItem(
            onClick = {
                onAddCategory()
                onHide()
            },
            text = {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.PlusOne,
                        contentDescription = "",
                        tint= MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = modifier.width(6.dp))
                    Text(
                        text = stringResource(R.string.create_new_category),
                        style = MaterialTheme.typography.displaySmall.copy(
                            color=MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
            }
        )
    }
){
    val ctx = LocalContext.current
    DropdownMenu(
        expanded = show,
        onDismissRequest = {
            onHide()
        },
        properties = PopupProperties(
            dismissOnBackPress = true,
            focusable = true,
            dismissOnClickOutside = true,
            excludeFromSystemGesture = true,
            clippingEnabled = true,
            securePolicy = SecureFlagPolicy.SecureOff
        ),
        modifier = modifier.background(MaterialTheme.colorScheme.background)
    ) {
        DropdownMenuItem(
            onClick = {
                onPick(Category(
                    categoryId = ctx.getString(R.string.no_category),
                    categoryName  = ctx.getString(R.string.no_category),
                    createdAt = OffsetDateTime.now().toString(),
                    updatedAt = OffsetDateTime.now().toString()
                ))
                onHide()
            },
            text = {
                Text(
                    text = stringResource(id = R.string.no_category),
                    style= MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        )
        listCategory.forEach {
            DropdownMenuItem(
                onClick = {
                    onPick(it)
                    onHide()
                },
                text = {
                    Text(
                        text = it.categoryName.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color=MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
            )
        }
        buttonAddCategory.invoke()

    }
}

@Preview
@Composable
fun PreviewDropdownCategory(){
    BaseMainApp {
        DropdownPickCategory(
            show = true,
            listCategory = listOf()
        )
    }
}