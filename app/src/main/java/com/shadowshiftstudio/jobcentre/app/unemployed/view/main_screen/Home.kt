package com.shadowshiftstudio.jobcentre.app.unemployed.view.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shadowshiftstudio.jobcentre.app.card.UnemployedCard
import com.shadowshiftstudio.jobcentre.app.card.VacancyCard
import com.shadowshiftstudio.jobcentre.app.employer.view.main_screens.HomeButtons
import com.shadowshiftstudio.jobcentre.app.employer.view_model.VacanciesViewModel
import com.shadowshiftstudio.jobcentre.app.unemployed.view_model.HomeViewModel
import com.shadowshiftstudio.jobcentre.domain.model.response.GetJobVacancyResponse

@Composable
fun Home(
    homeViewModel: HomeViewModel
) {
    val navController = rememberNavController()

    LaunchedEffect(homeViewModel) {
        homeViewModel.getAllVacancy()
    }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(23.dp)
            ) {
                HomeButtons({}, {})
                Spacer(modifier = Modifier.height(20.dp))
                VacanciesList(homeViewModel, navController)
            }
        }

        composable("vacancy_full_screen") {

        }
    }
}

@Composable
fun VacanciesList(
    viewModel: HomeViewModel,
    navController: NavController
) {
    val allVacanciesState = remember { mutableStateOf<List<GetJobVacancyResponse>?>(null) }

    val allVacanciesObserver = Observer<List<GetJobVacancyResponse>> { allUnemployed ->
        allVacanciesState.value = allUnemployed
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
                    com.shadowshiftstudio.jobcentre.app.unemployed.view.card.VacancyCard(vacancy = allVacanciesState.value!![index], navController, viewModel)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    )
}