package com.shadowshiftstudio.jobcentre.app.employer.view.unemployed_screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shadowshiftstudio.jobcentre.app.card.ApplyCard
import com.shadowshiftstudio.jobcentre.app.card.UnemployedCard
import com.shadowshiftstudio.jobcentre.app.employer.view_model.VacancyScreenViewModel
import com.shadowshiftstudio.jobcentre.domain.model.response.GetAppliesForVacancies
import com.shadowshiftstudio.jobcentre.domain.model.response.GetJobVacancyResponse

@Composable
fun VacancyScreen(vacancyResponse: GetJobVacancyResponse, navController: NavHostController) {
    val viewModel = VacancyScreenViewModel(LocalContext.current)
    viewModel.vacancy = vacancyResponse

    LaunchedEffect(viewModel) {
        viewModel.getAppliesVacancy()
    }

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                AppliesList(viewModel = viewModel, navController = navController)
            }
}

@Composable
fun AppliesList(viewModel: VacancyScreenViewModel, navController: NavController) {
    val allVacanciesState = remember { mutableStateOf<GetAppliesForVacancies?>(null) }

    val allVacanciesObserver = Observer<GetAppliesForVacancies> { allVacancies ->
        allVacanciesState.value = allVacancies
    }

    DisposableEffect(viewModel) {
        viewModel.applies.observeForever(allVacanciesObserver)

        onDispose {
            viewModel.applies.removeObserver(allVacanciesObserver)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        content = {
            allVacanciesState.value?.unemployedDto?.let {
                items(count = it.size) { index ->
                    if (allVacanciesState.value != null) {
                        if (allVacanciesState.value?.unemployedDto != null) {
                            ApplyCard(unemployed = allVacanciesState.value?.unemployedDto!![index], navController = navController, viewModel = viewModel)
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                }
            }
        }
    )
}