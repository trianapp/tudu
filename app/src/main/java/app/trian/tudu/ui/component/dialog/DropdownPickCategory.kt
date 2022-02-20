package app.trian.tudu.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.SecureFlagPolicy
import app.trian.tudu.R
import app.trian.tudu.data.local.Category
import app.trian.tudu.ui.theme.HexToJetpackColor
import app.trian.tudu.ui.theme.TuduTheme
import compose.icons.Octicons
import compose.icons.octicons.Plus16

/**
 * Dropdown
 * author Trian Damai
 * created_at 31/01/22 - 20.59
 * site https://trian.app
 */
@Composable
fun DropdownPickCategory(
    modifier:Modifier=Modifier,
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
            }
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Octicons.Plus16,
                    contentDescription = "",
                    tint=MaterialTheme.colors.onBackground
                )
                Spacer(modifier = modifier.width(6.dp))
                Text(
                    text = stringResource(R.string.create_new_category),
                    style = MaterialTheme.typography.button.copy(
                        color=MaterialTheme.colors.onBackground
                    )
                )
            }
        }
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
        modifier = modifier.background(MaterialTheme.colors.background)
    ) {
        DropdownMenuItem(
            onClick = {
                onPick(Category(
                    categoryId = ctx.getString(R.string.no_category),
                    name = ctx.getString(R.string.no_category),
                    created_at = 0,
                    updated_at = 0,
                    color = HexToJetpackColor.Blue
                ))
                onHide()
            }
        ) {
            Text(
                text = stringResource(id = R.string.no_category),
                style= MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.onBackground
                )
                )
        }
        listCategory.forEach {
            DropdownMenuItem(
                onClick = {
                    onPick(it)
                    onHide()
                }
            ) {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.body2.copy(
                        color=MaterialTheme.colors.onBackground
                    )
                )
            }
        }
        buttonAddCategory.invoke()

    }
}

@Preview
@Composable
fun PreviewDropdownCategory(){
    TuduTheme {
        DropdownPickCategory(
            show = true,
            listCategory = listOf()
        )
    }
}