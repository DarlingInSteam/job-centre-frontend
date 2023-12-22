package com.shadowshiftstudio.jobcentre.app.employer.view.unemployed_screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.shadowshiftstudio.jobcentre.domain.model.response.GetJobVacancyResponse

@Composable
fun VacancyScreen(vacancyResponse: GetJobVacancyResponse) {
    Text(text = vacancyResponse.jobVacancy.jobTitle)
}