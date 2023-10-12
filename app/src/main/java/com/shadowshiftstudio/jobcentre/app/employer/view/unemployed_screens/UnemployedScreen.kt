package com.shadowshiftstudio.jobcentre.app.employer.view.unemployed_screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.shadowshiftstudio.jobcentre.domain.model.entity.Unemployed

@Composable
fun UnemployedScreen(
    navController: NavController,
    unemployed: Unemployed
) {
    Text(text = unemployed.fullName)
}