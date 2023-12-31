package com.shadowshiftstudio.jobcentre.app.employer.view.unemployed_screens

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.shadowshiftstudio.jobcentre.app.card.Abilities
import com.shadowshiftstudio.jobcentre.app.card.VacancyCard
import com.shadowshiftstudio.jobcentre.app.employer.view.main_screens.AddNewVacancy
import com.shadowshiftstudio.jobcentre.app.employer.view_model.HomeViewModel
import com.shadowshiftstudio.jobcentre.app.employer.view_model.VacanciesViewModel
import com.shadowshiftstudio.jobcentre.data.app.secure_data.SecureStore
import com.shadowshiftstudio.jobcentre.domain.model.entity.Unemployed
import com.shadowshiftstudio.jobcentre.domain.model.enum.EducationLevel
import com.shadowshiftstudio.jobcentre.domain.model.enum.getRussianEducationLevel
import com.shadowshiftstudio.jobcentre.domain.model.request.CreateJobRequirementsRequest
import com.shadowshiftstudio.jobcentre.domain.model.request.CreateJobVacancyRequest
import com.shadowshiftstudio.jobcentre.domain.model.response.GetAppliesForVacancies
import com.shadowshiftstudio.jobcentre.domain.model.response.GetJobVacancyResponse
import kotlinx.coroutines.launch

@Composable
fun UnemployedScreen(
    navController: NavController,
    unemployed: Unemployed,
    homeViewModel: HomeViewModel
) {
    val state = rememberScrollState()
    var addNewVacancySheetVisible by remember { mutableStateOf(false) }
    var vacanciesViewModel = VacanciesViewModel(
        LocalContext.current)

    val experienceText = when (val experience = unemployed.workExperience) {
        1 -> "1 год"
        in 2..4 -> "$experience года"
        in 5..20 -> "$experience лет"
        else -> "Более 20 лет"
    }

    val ageText = when (val age = unemployed.age) {
        1 -> "1 год"
        in 2..4, in 22..24, in 32..34, in 42..44 , in 52..54 , in 62..64 -> "$age года"
        in 5..20, in 25..30, in 35..40, in 45..50 , in 55..60 -> "$age лет"
        else -> "$age год"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Default.ArrowBack, "", modifier = Modifier
                        .height(28.dp)
                        .width(28.dp)
                )
            }
            Text(text = "Просмотр профиля", fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 23.dp, end = 23.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = unemployed.fullName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = unemployed.speciality,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Опыт работы: $experienceText",
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Возраст: $ageText",
                )
            }
            AsyncImage(
                model = unemployed.unemployedPhoto ?: "https://nusho.com.ua/ckeditor-uploads/1-4.jpg",
                contentDescription = "Photo",
                modifier = Modifier
                    .size(height = 120.dp, width = 80.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop,
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Divider(thickness = 3.dp)

        Spacer(modifier = Modifier.height(15.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 23.dp, end = 23.dp)
        ) {
            Text(text = "Образование", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Уровень образования: ${getRussianEducationLevel(unemployed.educationLevel)}",
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Образовательное учреждение: ${unemployed.educationalInstitution}",
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Год окончания учреждения: ${unemployed.educationDocumentData}"
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 23.dp, end = 23.dp)
        ) {
            Text(text = "Ключевые навыки", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Abilities(abilities = unemployed.abilities)
        }

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 23.dp, end = 23.dp)
        ) {
            Text(text = "Обо мне", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = unemployed.aboutMe ?: "На данный момент информация не заполнялась.")
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { addNewVacancySheetVisible = true },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 23.dp)
        ) {
            Text(text = "Пригласить на вакансию")
        }
    }

    AnimatedVisibility(
        visible = addNewVacancySheetVisible,
        enter = slideInVertically(initialOffsetY = { height -> height }, animationSpec = tween()),
        exit = slideOutVertically(targetOffsetY = { height -> height }, animationSpec = tween()),
        content = {
            AddNewVacancy(onClose = { addNewVacancySheetVisible = false }, homeViewModel, vacanciesViewModel, navController)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewVacancy(onClose: () -> Unit, viewModel: HomeViewModel, vacanciesViewModel: VacanciesViewModel, navController: NavController) {
    val scaffoldState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(vacanciesViewModel) {
        vacanciesViewModel.getVacancies()
    }

    val allVacanciesState = remember { mutableStateOf<List<GetJobVacancyResponse>?>(null) }

    val allVacanciesObserver = Observer<List<GetJobVacancyResponse>> { allVacancies ->
        allVacanciesState.value = allVacancies
    }

    DisposableEffect(viewModel) {
        vacanciesViewModel.allVacancies.observeForever(allVacanciesObserver)

        onDispose {
            vacanciesViewModel.allVacancies.removeObserver(allVacanciesObserver)
        }
    }


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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                content = {
                    allVacanciesState.value?.let {
                        items(count = it.size) { index ->
                            VacancyCard(vacancy = allVacanciesState.value!![index], navController = navController, viewModel = vacanciesViewModel, 1) {
                                coroutineScope.launch {
                                    viewModel.inviteUnemployed(
                                        viewModel.unemployedFullScreen.id,
                                        allVacanciesState.value!![index].jobVacancy.id
                                    )
                                    onClose()
                                }
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                }
            )
        }
    }
}