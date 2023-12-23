package com.shadowshiftstudio.jobcentre.app.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shadowshiftstudio.jobcentre.app.employer.view_model.HomeViewModel
import com.shadowshiftstudio.jobcentre.app.employer.view_model.VacancyScreenViewModel
import com.shadowshiftstudio.jobcentre.domain.model.entity.Unemployed
import kotlinx.coroutines.launch

@Composable
fun ApplyCard(viewModel: VacancyScreenViewModel, unemployed: Unemployed, navController: NavController) {
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(23.dp)
    ) {
        UnemployedCard(unemployed = unemployed, navController = navController, viewModel = HomeViewModel(
            LocalContext.current))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                coroutineScope.launch {
                    viewModel.acceptUnemployed(unemployed.id, viewModel.vacancy.jobVacancy.id)
                    viewModel.getAppliesVacancy()
                }
            }) {
                Text(text = "Принять")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                coroutineScope.launch {
                    viewModel.rejectUnemployed(unemployed.id, viewModel.vacancy.jobVacancy.id)
                    viewModel.getAppliesVacancy()
                }
            }) {
                Text(text = "Отказать")
            }
        }

    }
}