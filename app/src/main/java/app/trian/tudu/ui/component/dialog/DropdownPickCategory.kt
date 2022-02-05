package app.trian.tudu.ui.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
    onAddCategory:()->Unit={}
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
        )
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
            Text(text = stringResource(id = R.string.no_category))
        }
        listCategory.forEach {
            DropdownMenuItem(
                onClick = {
                    onPick(it)
                    onHide()
                }
            ) {
                Text(text = it.name)
            }
        }
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
                Icon(imageVector = Octicons.Plus16, contentDescription = "")
                Spacer(modifier = modifier.width(6.dp))
                Text(text = stringResource(R.string.create_new_category))
            }
        }
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