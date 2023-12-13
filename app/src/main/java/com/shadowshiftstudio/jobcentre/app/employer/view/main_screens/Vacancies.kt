package com.shadowshiftstudio.jobcentre.app.employer.view.main_screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shadowshiftstudio.jobcentre.app.employer.view_model.VacanciesViewModel

@Composable
fun Vacancies(
    vacanciesViewModel: VacanciesViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "vacancies") {
        composable("vacancies") {

        }
        composable("vacancy") {

        }
    }
}