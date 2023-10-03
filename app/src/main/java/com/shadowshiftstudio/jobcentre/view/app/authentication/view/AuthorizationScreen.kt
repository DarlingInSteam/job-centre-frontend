package com.shadowshiftstudio.jobcentre.view.app.authentication.view

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shadowshiftstudio.jobcentre.view.app.authentication.view_model.AuthorizationViewModel
import com.shadowshiftstudio.jobcentre.view.app.authentication.view_model.RegistrationViewModel
import com.shadowshiftstudio.jobcentre.view.app.theme.md_theme_dark_surfaceVariant
import com.shadowshiftstudio.jobcentre.view.app.theme.md_theme_light_error
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AuthorizationScreen(navController: NavController, isAuthorization: () -> Unit) {
    val navControllerAuthorization = rememberNavController()
    val context = LocalContext.current
    val viewModel: RegistrationViewModel = RegistrationViewModel(context)

    NavHost(navController = navControllerAuthorization, startDestination = "main") {
        composable("main") {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                content = { AuthorizationContent(navController) {
                    isAuthorization()
                } },
                bottomBar = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Ещё нет аккаунта?",
                            textAlign = TextAlign.Justify,
                            color = md_theme_dark_surfaceVariant,
                            modifier = Modifier.clickable { navControllerAuthorization.navigate("registrationScreen") }
                                .padding(bottom = 11.dp)
                        )
                    }
                }
            )
        }
        composable("registrationScreen") {
            RegistrationScreen(navControllerAuthorization, viewModel)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AuthorizationContent(navController: NavController, isAuthorization: () -> Unit) {
    val context = LocalContext.current
    val viewModelLogin: AuthorizationViewModel = AuthorizationViewModel(context)
    val bringIntoViewRequester = BringIntoViewRequester()
    val coroutineScope = rememberCoroutineScope()

    var isTextVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 23.dp, end = 23.dp),
        verticalArrangement = Arrangement.Center
    )
    {
        LoginTextField(viewModelLogin, bringIntoViewRequester)

        Spacer(modifier = Modifier.height(20.dp))

        PhoneTextField(viewModelLogin, bringIntoViewRequester)

        Spacer(modifier = Modifier.height(20.dp))

        PasswordTextField("Пароль", viewModelLogin, bringIntoViewRequester)

        Spacer(modifier = Modifier.height(11.dp))

        Text(text = "",
            modifier = Modifier.clickable { /*TODO*/ }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier.fillMaxWidth()
                .bringIntoViewRequester(bringIntoViewRequester),
            onClick = {
                coroutineScope.launch {
                    viewModelLogin.login()
                }

                isAuthorization()
            },
            content = { Text(text = "Войти", fontSize = 18.sp) }
        )
        if(isTextVisible) {
            Text(
                text = "Неверный логин или пароль",
                color = md_theme_light_error,
                fontSize = 12.sp,
                textAlign = TextAlign.Left
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginTextField(viewModelLogin: AuthorizationViewModel, bringIntoViewRequester: BringIntoViewRequester ) {
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    TextField(
        value = viewModelLogin.login.value,
        onValueChange = { newText -> viewModelLogin.login.value = newText },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .onFocusEvent { event ->
                if (event.isFocused) {
                    coroutineScope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            },
        placeholder = { Text("Логин") },
        label = { Text("Логин") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhoneTextField(viewModelLogin: AuthorizationViewModel, bringIntoViewRequester: BringIntoViewRequester ) {
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    TextField(
        value = viewModelLogin.phone.value,
        onValueChange = { newText -> viewModelLogin.phone.value = newText },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .onFocusEvent { event ->
                if (event.isFocused) {
                    coroutineScope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            },
        placeholder = { Text("Номер телефона") },
        label = { Text("Номер телефона") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PasswordTextField(Hint: String, viewModelLogin: AuthorizationViewModel, bringIntoViewRequester: BringIntoViewRequester) {
    var passwordVisability by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    TextField(
        value = viewModelLogin.password.value,
        onValueChange = { newText -> viewModelLogin.password.value = newText },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .onFocusEvent { event ->
                if (event.isFocused) {
                    coroutineScope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            },
        placeholder = { Text(Hint) },
        label = { Text(Hint) },
        trailingIcon = {
            IconButton(onClick = { passwordVisability = !passwordVisability }) {
                Icon(
                    if (passwordVisability) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    ""
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        ),
        visualTransformation = if (passwordVisability) VisualTransformation.None
        else PasswordVisualTransformation()
    )
}