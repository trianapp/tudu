package app.trian.tudu.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.R
import app.trian.tudu.base.BaseMainApp

/**
 * Button
 * author Trian Damai
 * created_at 30/01/22 - 08.09
 * site https://trian.app
 */

@Composable
fun ButtonSecondary(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    fullWidth: Boolean = true,
    onClick: () -> Unit = {}
) {
    val m = if (fullWidth) modifier.fillMaxWidth() else modifier
    OutlinedButton(
        modifier = m
            .height(50.dp),
        contentPadding = PaddingValues(
            vertical = 4.dp,
            horizontal = 6.dp
        ),
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        ),
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.primary
        ),
        shape = MaterialTheme.shapes.medium,
        enabled = enabled
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun ButtonPrimary(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    fullWidth: Boolean = true,
    onClick: () -> Unit = {}
) {
    val m = if (fullWidth) modifier.fillMaxWidth() else modifier
    Button(
        modifier = m
            .height(50.dp),
        contentPadding = PaddingValues(
            vertical = 4.dp
        ),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = MaterialTheme.shapes.medium,
        enabled = enabled
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun ButtonSocial(
    modifier: Modifier = Modifier,
    text: String = "",
    enabled: Boolean = true,
    fullWidth: Boolean = true,
    onClick: () -> Unit = {}
) {
    val m = if (fullWidth) modifier.fillMaxWidth() else modifier
    OutlinedButton(
        modifier = m
            .height(50.dp),
        enabled = enabled,
        contentPadding = PaddingValues(
            vertical = 6.dp
        ),
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = Color.LightGray
        ),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(
            width = 1.dp,
            color = if (enabled) MaterialTheme.colorScheme.primary else Companion.LightGray
        ),
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_google_normal),
                contentDescription = "Continue With Google",
                modifier = modifier.align(Alignment.CenterStart)
            )
            Spacer(modifier = modifier.width(6.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                modifier = modifier.align(Alignment.Center)
            )
        }
    }
}


@Composable
fun ButtonIcon(
    modifier: Modifier = Modifier,
    text: String = "",
    icon: ImageVector = Icons.Outlined.Share,
    enabled: Boolean = true,
    fullWidth: Boolean = true,
    onClick: () -> Unit = {}
) {
    val m = if (fullWidth) modifier.fillMaxWidth() else modifier
    OutlinedButton(
        modifier = m
            .height(50.dp),
        enabled = enabled,
        contentPadding = PaddingValues(
            vertical = 6.dp
        ),
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContentColor = Color.LightGray
        ),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(
            width = 1.dp,
            color = if (enabled) MaterialTheme.colorScheme.onSurface else Companion.LightGray
        ),
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp)
        ) {
            Icon(imageVector = icon, contentDescription = "")
            Spacer(modifier = modifier.width(6.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                modifier = modifier.align(Alignment.Center)
            )
        }
    }
}


@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    widthDp = 450
)
@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    widthDp = 450
)
@Composable
fun PreviewButton() {
    BaseMainApp {
        Column {
            ButtonPrimary(text = "Sign In")
            Spacer(modifier = Modifier.height(10.dp))
            ButtonSecondary(text = "Create New Account")
            Spacer(modifier = Modifier.height(10.dp))
            ButtonSocial(text = "Continue With Google")
        }

    }
}