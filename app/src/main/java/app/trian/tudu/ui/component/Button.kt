package app.trian.tudu.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.tudu.R
import app.trian.tudu.ui.theme.RedGoogle
import app.trian.tudu.ui.theme.TuduTheme
import compose.icons.Octicons
import compose.icons.octicons.LogoGithub16

/**
 * Button
 * author Trian Damai
 * created_at 30/01/22 - 08.09
 * site https://trian.app
 */

@Composable
fun ButtonPrimary(
    modifier: Modifier=Modifier,
    text:String,
    onClick:()->Unit={}
) {
    Button(
        modifier= modifier
            .fillMaxWidth()
            .height(50.dp),
        contentPadding= PaddingValues(
            vertical = 4.dp
        ),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = text)
    }
}
@Composable
fun ButtonGoogle(
    modifier: Modifier=Modifier,
    text:String,
    onClick:()->Unit={}
) {
    Button(
        modifier= modifier
            .fillMaxWidth()
            .height(50.dp),
        contentPadding= PaddingValues(
            vertical = 4.dp
        ),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = RedGoogle,
            contentColor = MaterialTheme.colors.onPrimary
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_google_signin_button), contentDescription = "")
        Spacer(modifier = modifier.width(6.dp))
        Text(text = text)
    }
}
@Composable
fun ButtonSecondary(
    modifier: Modifier=Modifier,
    text:String,
    onClick:()->Unit={}
) {
    OutlinedButton(
        modifier= modifier
            .fillMaxWidth()
            .height(50.dp),
        contentPadding= PaddingValues(
            vertical = 6.dp
        ),
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colors.primary
        ),
        border= BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colors.primary
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = text)
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
fun PreviewButton(){
    TuduTheme {
        Column {
            ButtonPrimary(text = "Sign In")
            Spacer(modifier = Modifier.height(10.dp))
            ButtonSecondary(text = "Create New Account")
            Spacer(modifier = Modifier.height(10.dp))
            ButtonGoogle(text = "Continue With Google")
        }

    }
}