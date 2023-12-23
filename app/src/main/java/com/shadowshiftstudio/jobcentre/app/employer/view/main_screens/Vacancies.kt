package com.shadowshiftstudio.jobcentre.app.employer.view.main_screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shadowshiftstudio.jobcentre.app.card.VacancyCard
import com.shadowshiftstudio.jobcentre.app.employer.view.unemployed_screens.VacancyScreen
import com.shadowshiftstudio.jobcentre.app.employer.view_model.VacanciesViewModel
import com.shadowshiftstudio.jobcentre.data.app.secure_data.SecureStore
import com.shadowshiftstudio.jobcentre.domain.model.enum.EducationLevel
import com.shadowshiftstudio.jobcentre.domain.model.enum.getRussianEducationLevel
import com.shadowshiftstudio.jobcentre.domain.model.request.CreateJobRequirementsRequest
import com.shadowshiftstudio.jobcentre.domain.model.request.CreateJobVacancyRequest
import com.shadowshiftstudio.jobcentre.domain.model.response.GetJobVacancyResponse
import kotlinx.coroutines.launch

@Composable
fun Vacancies(
    vacanciesViewModel: VacanciesViewModel
) {
    val navController = rememberNavController()
    var addNewVacancySheetVisible by remember { mutableStateOf(false) }

    LaunchedEffect(vacanciesViewModel) {
        vacanciesViewModel.getVacancies()
    }

    NavHost(navController = navController, startDestination = "vacancies") {
        composable("vacancies") {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(23.dp),
            ) {
                VacancyButtons() {
                    addNewVacancySheetVisible = true
                }
                Spacer(modifier = Modifier.height(20.dp))
                VacancyList(vacanciesViewModel, navController)
            }
        }
        composable("vacancy") {
            VacancyScreen(vacanciesViewModel.vacancy, navController)
        }
    }

    AnimatedVisibility(
        visible = addNewVacancySheetVisible,
        enter = slideInVertically(initialOffsetY = { height -> height }, animationSpec = tween()),
        exit = slideOutVertically(targetOffsetY = { height -> height }, animationSpec = tween()),
        content = {
            AddNewVacancy(onClose = { addNewVacancySheetVisible = false }, vacanciesViewModel)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewVacancy(onClose: () -> Unit, viewModel: VacanciesViewModel) {
    val scaffoldState = rememberModalBottomSheetState()
    var jobName by remember { mutableStateOf("") }
    var jobType by remember { mutableStateOf("") }
    var jobSalary by remember { mutableStateOf("") }
    var ageRangeLower by remember { mutableStateOf("") }
    var ageRangeUpper by remember { mutableStateOf("") }
    var workExperience by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var lvl = EducationLevel.BACHELOR
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = { onClose() },
        sheetState = scaffoldState,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(23.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column{
                Text(text = "Название вакансии")
                OutlinedTextField(value = jobName, onValueChange = {
                    jobName = it
                })
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column{
                Text(text = "Тип работы")
                OutlinedTextField(value = jobType, onValueChange = {
                    jobType = it
                })
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column{
                Text(text = "Зарплата")
                OutlinedTextField(value = jobSalary, onValueChange = {
                    jobSalary = it
                })
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Требования к работе", modifier = Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(20.dp))
            Column{
                Text(text = "Минимальный возраст")
                OutlinedTextField(value = ageRangeLower, onValueChange = {
                    ageRangeLower = it
                })
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column{
                Text(text = "Максимальный возраст")
                OutlinedTextField(value = ageRangeUpper, onValueChange = {
                    ageRangeUpper = it
                })
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column{
                Text(text = "Трудовой стаж")
                OutlinedTextField(value = workExperience, onValueChange = {
                    workExperience = it
                })
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column{
                Text(text = "Образование")
                OutlinedTextField(value = getRussianEducationLevel(lvl), enabled = false ,onValueChange = {}, modifier = Modifier.clickable{ expanded = true })
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                Text("Начальное общее образование", modifier = Modifier
                    .padding(10.dp)
                    .clickable(onClick = {
                        lvl = EducationLevel.PRIMARY
                        expanded = false
                    }))
                Text("Основное общее образование", modifier = Modifier
                    .padding(10.dp)
                    .clickable(onClick = {
                        lvl = EducationLevel.BASIC
                        expanded = false
                    }))
                Text("Среднее общее образование", modifier = Modifier
                    .padding(10.dp)
                    .clickable(onClick = {
                        lvl = EducationLevel.SECONDARY
                        expanded = false
                    }))
                Text("Среднее профессиональное образование", modifier = Modifier
                    .padding(10.dp)
                    .clickable(onClick = {
                        lvl = EducationLevel.SECONDARY_PROFESSIONAL_QUALIFIED
                        expanded = false
                    }))
                Text("Среднее специалитетное образование", modifier = Modifier
                    .padding(10.dp)
                    .clickable(onClick = {
                        lvl = EducationLevel.SECONDARY_PROFESSIONAL_SPECIALIST
                        expanded = false
                    }))
                Text("Бакалавриат", modifier = Modifier
                    .padding(10.dp)
                    .clickable(onClick = {
                        lvl = EducationLevel.BACHELOR
                        expanded = false
                    }))
                Text("Магистратура", modifier = Modifier
                    .padding(10.dp)
                    .clickable(onClick = {
                        lvl = EducationLevel.MASTER
                        expanded = false
                    }))
                Text("Высшая квалификация", modifier = Modifier
                    .padding(10.dp)
                    .clickable(onClick = {
                        lvl = EducationLevel.POSTGRADUATE
                        expanded = false
                    }))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column{
                Button(onClick = {
                    var jobRequirements = CreateJobRequirementsRequest(
                        educationLevel = lvl,
                        ageRangeLower = ageRangeLower.toInt(),
                        ageRangeUpper = ageRangeUpper.toInt(),
                        workExperience = workExperience.toInt()
                    )
                    var request = CreateJobVacancyRequest(
                        jobType = jobType,
                        jobTitle = jobName,
                        salary = jobSalary.toInt(),
                        username = SecureStore.getUsername().toString(),
                        createJobRequirementsRequest = jobRequirements
                    )

                    onClose()

                    coroutineScope.launch {
                        viewModel.createVacancy(request)
                    }
                }) {
                    Text(text = "Создать вакансию")
                }
            }
        }
    }
}

@Composable
fun VacancyList(viewModel: VacanciesViewModel, navController: NavController) {
    val allVacanciesState = remember { mutableStateOf<List<GetJobVacancyResponse>?>(null) }

    val allVacanciesObserver = Observer<List<GetJobVacancyResponse>> { allVacancies ->
        allVacanciesState.value = allVacancies
    }

    DisposableEffect(viewModel) {
        viewModel.allVacancies.observeForever(allVacanciesObserver)

        onDispose {
            viewModel.allVacancies.removeObserver(allVacanciesObserver)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        content = {
            allVacanciesState.value?.let {
                items(count = it.size) { index ->
                    VacancyCard(vacancy = allVacanciesState.value!![index], navController = navController, viewModel = viewModel, 0, {})
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    )
}

@Composable
fun VacancyButtons(
    changeButtonSheetFilterVisible: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ExtendedFloatingActionButton(
            onClick = {
                changeButtonSheetFilterVisible()
            },
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .width(127.dp)
                .height(40.dp),
        ) {
            Icon(
                Icons.Default.FilterList,
                "Sort icon",
                modifier = Modifier
                    .width(18.dp)
                    .height(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Фильтры")
        }

        ExtendedFloatingActionButton(
            onClick = { /*TODO*/
                changeButtonSheetFilterVisible()
            },
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .width(146.dp)
                .height(40.dp),
        ) {
            Icon(
                Icons.Default.Add,
                "Sort icon",
                modifier = Modifier
                    .width(18.dp)
                    .height(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Добавить")
        }
    }
}