package com.shadowshiftstudio.jobcentre.app.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shadowshiftstudio.jobcentre.app.employer.view_model.VacanciesViewModel
import com.shadowshiftstudio.jobcentre.domain.model.enum.getRussianEducationLevel
import com.shadowshiftstudio.jobcentre.domain.model.response.GetJobVacancyResponse

@Composable
fun VacancyCard(vacancy: GetJobVacancyResponse, navController: NavController, viewModel: VacanciesViewModel, status: Int, onClick: () -> Unit) {

    val experienceText = when (val experience = vacancy.jobRequirement.workExperience) {
        1 -> "1 год"
        in 2..4 -> "$experience года"
        in 5..20 -> "$experience лет"
        else -> "Более 20 лет"
    }

    val ageLowerText = when (val age = vacancy.jobRequirement.ageRangeLower) {
        1 -> "1 год"
        in 2..4, in 22..24, in 32..34, in 42..44 , in 52..54 , in 62..64 -> "$age года"
        in 5..20, in 25..30, in 35..40, in 45..50 , in 55..60 -> "$age лет"
        else -> "$age год"
    }

    val ageUpperText = when (val age = vacancy.jobRequirement.ageRangeUpper) {
        1 -> "1 год"
        in 2..4, in 22..24, in 32..34, in 42..44 , in 52..54 , in 62..64 -> "$age года"
        in 5..20, in 25..30, in 35..40, in 45..50 , in 55..60 -> "$age лет"
        else -> "$age год"
    }

    Card(
        modifier = Modifier
            .clickable {
                if (status == 0) {
                    viewModel.vacancy = vacancy
                    navController.navigate("vacancy")
                }
                else {
                    onClick()
                }
            }
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = vacancy.jobVacancy.jobTitle,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = vacancy.jobVacancy.jobType,
                fontWeight = FontWeight.Bold
            )
            Text(
                text ="Заработная плата: ${vacancy.jobVacancy.salary.toString()}",
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Стаж: $experienceText",
            )
            Text(
                text = "Уровень образования: ${getRussianEducationLevel(vacancy.jobRequirement.educationLevel)}",
            )
            Text(
                text = "Минимальный и максимальный возраст: ${vacancy.jobRequirement.ageRangeLower}, ${vacancy.jobRequirement.ageRangeUpper}",
            )
        }
    }
}