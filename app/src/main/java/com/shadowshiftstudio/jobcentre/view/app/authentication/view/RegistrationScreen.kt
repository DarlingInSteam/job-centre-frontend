package com.shadowshiftstudio.jobcentre.view.app.authentication.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberRichTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.shadowshiftstudio.jobcentre.CreateLoginHint
import com.shadowshiftstudio.jobcentre.passwordRules
import com.shadowshiftstudio.jobcentre.EnterEmailHint
import com.shadowshiftstudio.jobcentre.EnterPasswordHint
import com.shadowshiftstudio.jobcentre.FillAllFields
import com.shadowshiftstudio.jobcentre.InputErrorMessage
import com.shadowshiftstudio.jobcentre.PasswordsDontMatch
import com.shadowshiftstudio.jobcentre.RegisterButtonText
import com.shadowshiftstudio.jobcentre.RepeatPasswordHint
import com.shadowshiftstudio.jobcentre.view.app.authentication.view_model.RegistrationViewModel
import com.shadowshiftstudio.jobcentre.view.app.theme.md_theme_light_error
import com.shadowshiftstudio.jobcentre.view.app.theme.md_theme_dark_onSurface
import com.shadowshiftstudio.jobcentre.view.app.theme.md_theme_dark_onSurfaceVariant
import com.shadowshiftstudio.jobcentre.view.app.theme.md_theme_dark_surface_container_higher
import com.shadowshiftstudio.jobcentre.model.enum.LoginStates
import com.shadowshiftstudio.jobcentre.model.enum.Role
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegistrationScreen(navController: NavHostController, viewModelRegistration: RegistrationViewModel) {
    val context = LocalContext.current
    var isTextVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val bringIntoViewRequester = BringIntoViewRequester()
    val scrollState = rememberScrollState()

    Column() {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Default.ArrowBack, "", modifier = Modifier
                        .height(28.dp)
                        .width(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(11.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 23.dp, end = 23.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top
        )
        {
            TypeTabs(viewModelRegistration)

            Spacer(modifier = Modifier.height(20.dp))

            if (viewModelRegistration.selectedTabIndex.intValue == 0) {
                LoginTextField(viewModelRegistration)
                Spacer(modifier = Modifier.height(20.dp))
                FieldsForUnemployed(coroutineScope, viewModelRegistration)
                Spacer(modifier = Modifier.height(20.dp))
                RegPasswordField(EnterPasswordHint, viewModelRegistration)
                Spacer(modifier = Modifier.height(20.dp))
                RepeatPasswordField(RepeatPasswordHint, viewModelRegistration)
            } else {
                LoginTextField(viewModelRegistration)
                Spacer(modifier = Modifier.height(20.dp))
                FieldsForEmployer(coroutineScope, viewModelRegistration)
                Spacer(modifier = Modifier.height(20.dp))
                RegPasswordField(EnterPasswordHint, viewModelRegistration)
                Spacer(modifier = Modifier.height(20.dp))
                RepeatPasswordField(RepeatPasswordHint, viewModelRegistration)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .bringIntoViewRequester(bringIntoViewRequester),
                onClick = {
                    if(viewModelRegistration.isAllDataEnteredUnemployed() && viewModelRegistration.selectedTabIndex.intValue == 0){
                        coroutineScope.launch {
                            viewModelRegistration.registration(Role.UNEMPLOYED)
                            if(viewModelRegistration.registerStatusLiveData.value == true) {
                                navController.popBackStack()
                            }
                        }
                    }
                    else if(viewModelRegistration.isAllDataEnteredEmployer() && viewModelRegistration.selectedTabIndex.intValue == 1) {
                        coroutineScope.launch {
                            viewModelRegistration.registration(Role.EMPLOYED)
                            viewModelRegistration.createEmployer()
                            if(viewModelRegistration.registerStatusLiveData.value == true) {
                                navController.popBackStack()
                            }
                        }
                    }
                    else isTextVisible = true},
                content = { Text(text = RegisterButtonText, fontSize = 18.sp) }
            )

            if(isTextVisible) {
                Text(
                    text = FillAllFields,
                    fontSize = 12.sp,
                    color = md_theme_light_error,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FieldsForEmployer(
    coroutineScope: CoroutineScope,
    viewModelRegistration: RegistrationViewModel,
) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = viewModelRegistration.phone.value,
        onValueChange = {
            viewModelRegistration.phone.value = it
        },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        placeholder = { Text("Номер телефона") },
        label = { Text("Номер телефона") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )

    Spacer(modifier = Modifier.height(20.dp))

    TextField(
        value = viewModelRegistration.employerName.value,
        onValueChange = {
            viewModelRegistration.employerName.value = it
        },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            ,
        placeholder = { Text("Название компании") },
        label = { Text("Название компании") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )

    Spacer(modifier = Modifier.height(20.dp))

    TextField(
        value = viewModelRegistration.address.value,
        onValueChange = {
            viewModelRegistration.address.value = it
        },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            ,
        placeholder = { Text("Адрес компании") },
        label = { Text("Адрес компании") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FieldsForUnemployed(
    coroutineScope: CoroutineScope,
    viewModelRegistration: RegistrationViewModel,
    
) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = viewModelRegistration.phone.value,
        onValueChange = {
            viewModelRegistration.phone.value = it
        },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            ,
        placeholder = { Text("Номер телефона") },
        label = { Text("Номер телефона") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )

    Spacer(modifier = Modifier.height(20.dp))

    TextField(
        value = viewModelRegistration.fullName.value,
        onValueChange = {
            viewModelRegistration.fullName.value = it
        },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            ,
        placeholder = { Text("ФИО") },
        label = { Text("ФИО") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )

    Spacer(modifier = Modifier.height(20.dp))

    TextField(
        value = viewModelRegistration.age.value,
        onValueChange = {
            viewModelRegistration.age.value = it
        },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            ,
        placeholder = { Text("Возраст") },
        label = { Text("Возраст") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )

    Spacer(modifier = Modifier.height(20.dp))

    DropdownTextField(listOf("Начальное общее", "Основное общее", "Среднее общее", "Среднее профессиональное", "Бакалавриат", "Специалитет, магистратура", "Высшая квалификация"))

    Spacer(modifier = Modifier.height(20.dp))

    TextField(
        value = viewModelRegistration.educationalInstitution.value,
        onValueChange = {
            viewModelRegistration.educationalInstitution.value = it
        },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            ,
        placeholder = { Text("Образовательное учреждение") },
        label = { Text("Образовательное учреждение") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )

    Spacer(modifier = Modifier.height(20.dp))

    TextField(
        value = viewModelRegistration.educationDocumentData.value,
        onValueChange = {
            viewModelRegistration.educationDocumentData.value = it
        },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            ,
        placeholder = { Text("Номер документа об оброзазовании") },
        label = { Text("Номер документа об оброзазовании") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )

    Spacer(modifier = Modifier.height(20.dp))

    TextField(
        value = viewModelRegistration.speciality.value,
        onValueChange = {
            viewModelRegistration.speciality.value = it
        },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            ,
        placeholder = { Text("Специальность") },
        label = { Text("Специальность") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )

    Spacer(modifier = Modifier.height(20.dp))

    TextField(
        value = viewModelRegistration.workExperience.value,
        onValueChange = {
            viewModelRegistration.workExperience.value = it
        },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            ,
        placeholder = { Text("Опыт работы") },
        label = { Text("Опыт работы") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )

    Spacer(modifier = Modifier.height(20.dp))

    TextField(
        value = viewModelRegistration.passportNumber.value,
        onValueChange = {
            viewModelRegistration.passportNumber.value = it
        },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            ,
        placeholder = { Text("Номер и серия паспорта") },
        label = { Text("Номер и серия паспорта") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )

    Spacer(modifier = Modifier.height(20.dp))

    TextField(
        value = viewModelRegistration.passportIssueDate.value,
        onValueChange = {
            viewModelRegistration.passportIssueDate.value = it
        },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            ,
        placeholder = { Text("Дата выдачи пасорта") },
        label = { Text("Дата выдачи пасорта") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )

    Spacer(modifier = Modifier.height(20.dp))

    TextField(
        value = viewModelRegistration.passportIssueBy.value,
        onValueChange = {
            viewModelRegistration.passportIssueBy.value = it
        },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            ,
        placeholder = { Text("Кем выдан паспорт") },
        label = { Text("Кем выдан паспорт") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )

    Spacer(modifier = Modifier.height(20.dp))

    TextField(
        value = viewModelRegistration.dateOfBirth.value,
        onValueChange = {
            viewModelRegistration.dateOfBirth.value = it
        },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            ,
        placeholder = { Text("Дата рождения") },
        label = { Text("Дата рождения") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )

    Spacer(modifier = Modifier.height(20.dp))

    TextField(
        value = viewModelRegistration.address.value,
        onValueChange = {
            viewModelRegistration.address.value = it
        },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            ,
        placeholder = { Text("Адрес прописки") },
        label = { Text("Адрес прописки") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )

    Spacer(modifier = Modifier.height(20.dp))

    TextField(
        value = viewModelRegistration.photo.value,
        onValueChange = {
            viewModelRegistration.photo.value = it
        },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            ,
        placeholder = { Text("Ссылка на фотографию") },
        label = { Text("Ссылка на фотографию") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )
}

@Composable
fun DropdownTextField(items: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(items.firstOrNull()) }
    var iconAfter by remember {
        mutableStateOf(Icons.Default.ArrowRight)
    }
    var iconBefore by remember {
        mutableStateOf(Icons.Default.ArrowDropDown)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .clickable { expanded = true }
                .background(md_theme_dark_surface_container_higher),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                modifier = Modifier
                    .height(60.dp)
                    .padding(top = 20.dp, start = 15.dp),
                value = TextFieldValue(selectedItem ?: ""),
                enabled = false,
                onValueChange = { /* Disable editing */ },
                textStyle = TextStyle(
                    color = md_theme_dark_onSurfaceVariant,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start
                ),
            )
            Icon(
                if (expanded) iconBefore else iconAfter, "", modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 10.dp)
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd),
                thickness = 1.dp,
                color = md_theme_dark_onSurfaceVariant
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(md_theme_dark_surface_container_higher)
                .padding(start = 23.dp, end = 23.dp)
                .fillMaxWidth()
                ,
        ) {
            items.forEach { item ->
                TextButton(
                    onClick = {
                        selectedItem = item
                        expanded = false
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(),
                    colors = ButtonColors(
                        md_theme_dark_surface_container_higher,
                        md_theme_dark_onSurfaceVariant,
                        Color.White,
                        Color.White
                    )

                ) {
                    Text(text = item)
                }
            }
        }
    }
}

@Composable
fun TypeTabs(viewModel: RegistrationViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TabRow(
            selectedTabIndex = viewModel.selectedTabIndex.intValue,
            modifier = Modifier
                .height(60.dp),
            divider = ({})
        ) {
            viewModel.tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = false,
                    modifier = Modifier
                        .height(60.dp),
                    onClick = {
                        viewModel.selectedTabIndex.intValue = index
                    }
                ) {
                    Text(
                        text = title,
                        modifier = Modifier.background(Color.Transparent),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginTextField(viewModelRegistration: RegistrationViewModel, )
{
    var isLoginError by remember { mutableStateOf(false) }
    var loginErrorMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    TextField(
        value = viewModelRegistration.login.value,
        onValueChange = {
            viewModelRegistration.login.value = it
            var res = viewModelRegistration.isLoginValid(viewModelRegistration.login.value)
            if (res == LoginStates.VALID)
                isLoginError = false
            else if (res == LoginStates.INVALID_CHARACTERS) {
                isLoginError = true
                loginErrorMessage = "Логин может содержать только буквы английского алфавита"
            } else if (res == LoginStates.INVALID_LENGTH) {
                isLoginError = true
                loginErrorMessage = "Допустимая длина от 3 до 20 символов"
            }
        },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            ,
        placeholder = { Text(CreateLoginHint) },
        label = { Text(CreateLoginHint) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        )
    )
    if (isLoginError && !viewModelRegistration.login.value.isEmpty()) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = loginErrorMessage,
            fontSize = 12.sp,
            color = md_theme_light_error
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun RegPasswordField(Hint: String, viewModel: RegistrationViewModel, )
{
    var passwordVisability by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    TextField(
        value = viewModel.password.value,
        onValueChange = {
            viewModel.password.value = it
            isPasswordError = !viewModel.isPasswordValid(viewModel.password.value)},
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            ,
        placeholder = { Text(Hint) },
        label = { Text(Hint) },
        trailingIcon =
        {
            val tooltipState = rememberRichTooltipState(isPersistent = true)
            val scope = rememberCoroutineScope()
            RichTooltipBox(
                text = {
                    Text(text = passwordRules,
                        modifier = Modifier.padding(10.dp))
                },
                tooltipState = tooltipState,
            ) {
                IconButton(
                    onClick = {  scope.launch { tooltipState.show() } },
                    modifier = Modifier.tooltipTrigger()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        tint = if(isPasswordError == true) md_theme_light_error else md_theme_dark_onSurface,
                        contentDescription = ""
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        ),
        visualTransformation = if (passwordVisability) VisualTransformation.None
        else PasswordVisualTransformation()
    )
    if (isPasswordError && viewModel.password.value.isNotEmpty()) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = InputErrorMessage,
            fontSize = 12.sp,
            color = md_theme_light_error
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RepeatPasswordField(Hint: String, viewModel: RegistrationViewModel, )
{
    var passwordVisability by remember { mutableStateOf(false) }
    var isPasswordsEqual by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    TextField(
        value = viewModel.repeatPassword.value,
        onValueChange = { viewModel.repeatPassword.value = it
            isPasswordsEqual =viewModel.isPasswordsMatch()},
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            ,
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
    if (isPasswordsEqual == false && viewModel.repeatPassword.value.isNotEmpty()) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = PasswordsDontMatch,
            fontSize = 12.sp,
            color = md_theme_light_error
        )
    }
}