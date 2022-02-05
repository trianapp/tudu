package app.trian.tudu.ui.pages.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.R
import app.trian.tudu.ui.component.AppbarAuth
import app.trian.tudu.ui.component.ButtonPrimary
import app.trian.tudu.ui.component.task.FormInput
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.UserViewModel

@Composable
fun PagesRegister(
    modifier: Modifier=Modifier,
    router: NavHostController
) {
    val ctx = LocalContext.current
    val userViewModel = hiltViewModel<UserViewModel>()

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var username by remember {
        mutableStateOf("")
    }

    fun processRegister(){
        if(email.isBlank() || password.isBlank() || username.isBlank()){
            Toast.makeText(ctx,"Please fill all form?",Toast.LENGTH_SHORT).show()
            return
        }
        userViewModel.registerWithEmailAndPassword(username,email,password){
            success, message ->
            if(success){
                router.popBackStack()
            }
            Toast.makeText(ctx,"login $success $message", Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        topBar = {
            AppbarAuth(){
                router.popBackStack()
            }
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(
                    horizontal = 36.dp
                )
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = stringResource(R.string.title_register),
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Row {
                Text(
                    text = stringResource(R.string.label_already_have_account),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light
                    )
                )
                Text(
                    text = stringResource(id = R.string.btn_signin),
                    modifier=modifier.clickable {
                        router.popBackStack()
                    },
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colors.primary
                    )
                )
            }
            Spacer(modifier = modifier.height(20.dp))
            Column(
                modifier= modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                FormInput(
                    initialValue = username,
                    label = {
                        Text(text = stringResource(R.string.label_input_username))
                    },
                    placeholder = stringResource(R.string.placeholder_input_username),
                    onChange = {
                        username = it
                    }
                )
                Spacer(modifier = modifier.height(16.dp))
                FormInput(
                    initialValue = email,
                    label = {
                        Text(text = stringResource(id = R.string.label_input_email))
                    },
                    placeholder = stringResource(id = R.string.placeholder_input_email),
                    onChange = {
                        email = it
                    }
                )
                Spacer(modifier = modifier.height(16.dp))
                FormInput(
                    initialValue = password,
                    label = {
                        Text(text = stringResource(id = R.string.label_input_password))
                    },
                    showPasswordObsecure = true,
                    placeholder = stringResource(id = R.string.placeholder_input_password),
                    onChange = {
                        password = it
                    }
                )
            }
            Spacer(modifier = modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = false,
                    onCheckedChange ={ },
                    modifier=modifier.clip(CircleShape)
                )
                Text(text = stringResource(R.string.text_license_agreement))
            }
            Spacer(modifier = modifier.height(20.dp))
            ButtonPrimary(text = stringResource(R.string.btn_continue)){
                processRegister()
            }
        }
    }
}

@Preview
@Composable
fun PreviewPagesRegister(){
    TuduTheme {
        PagesRegister(router=rememberNavController())
    }
}