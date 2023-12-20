package com.shadowshiftstudio.jobcentre.app.employer.view.main_screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.shadowshiftstudio.jobcentre.app.employer.view_model.CompanyViewModel
import com.shadowshiftstudio.jobcentre.domain.model.entity.Employer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.annotations.Async

@Composable
fun Company(
    viewModel: CompanyViewModel
) {
    LaunchedEffect(viewModel) {
        viewModel.getEmployer()
    }

    CompanyInfo(viewModel)
}

@Composable
fun CompanyInfo(
    viewModel: CompanyViewModel
) {
    val employer = remember { mutableStateOf<Employer?>(null) }

    val employerObserver = Observer<Employer> { newEmployer ->
        employer.value = newEmployer
    }

    DisposableEffect(viewModel) {
        viewModel.employer.observeForever(employerObserver)

        onDispose {
            viewModel.employer.removeObserver(employerObserver)
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(
                23.dp
            )
    ){
        Column (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row {
                Text(text = "Название компании", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                employer.value?.let { it.employerName?.let { it1 -> EditableTextField(it1, {}, {},
                    employer.value!!, viewModel, 1) } }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row {
                Text(text = "Адрес компании", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                employer.value?.let { it.address?.let { it1 -> EditableTextField(it1, {}, {},
                    employer.value!!, viewModel, 2) } }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row {
                Text(text = "О компании", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                employer.value?.let { it.aboutCompany?.let { it1 -> EditableTextField(it1, {}, {},
                    employer.value!!, viewModel, 3) } }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row {
                Text(text = "Ссылка на фотографию", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                employer.value?.let { it.companyPhoto?.let { it1 -> EditableTextField(it1, {}, {},
                    employer.value!!, viewModel, 4) } }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                AsyncImage(
                    model = employer.value?.companyPhoto,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            }
        }
    }
}

@Composable
fun EditableTextField(
    text: String,
    onTextChanged: (String) -> Unit,
    onEditClick: () -> Unit,
    employer: Employer,
    viewModel: CompanyViewModel,
    editIcon: Int
) {
    var isEditing by remember { mutableStateOf(false) }
    var editableText by remember { mutableStateOf(text) }
    val coroutineScope = rememberCoroutineScope()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (isEditing) {
            TextField(
                value = editableText,
                onValueChange = {
                    editableText = it
                },
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = {
                    isEditing = false
                    onTextChanged(editableText)

                    if (editIcon == 1) {
                        coroutineScope.launch {
                            viewModel.addNewName(editableText, employer.id)
                        }
                    }

                    if (editIcon == 2) {
                        coroutineScope.launch {
                            viewModel.addNewAddress(editableText, employer.id)
                        }
                    }

                    if (editIcon == 3) {
                        coroutineScope.launch {
                            viewModel.addAboutCompany(editableText, employer.id)
                        }
                    }

                    if (editIcon == 4) {
                        coroutineScope.launch {
                            viewModel.addPhoto(editableText, employer.id)
                        }
                    }
                }
            ) {
                Icon(Icons.Default.Check, contentDescription = "Принять")
            }
        } else {
            Text(
                text = text,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = {
                    isEditing = true
                    onEditClick()
                }
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Редактировать")
            }
        }
    }
}
