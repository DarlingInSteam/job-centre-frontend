package com.shadowshiftstudio.jobcentre.view.employer.view.main_screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import com.shadowshiftstudio.jobcentre.model.entity.Unemployed
import com.shadowshiftstudio.jobcentre.view.card.UnemployedCard
import com.shadowshiftstudio.jobcentre.view.employer.view_model.HomeViewModel

@Composable
fun Home(
    viewModel: HomeViewModel
) {
    LaunchedEffect(viewModel) {
        viewModel.getAllUnemployed()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(23.dp)
    ) {
        UnemployedList(viewModel = viewModel)
    }
}

@Composable
fun UnemployedList(viewModel: HomeViewModel) {
    val allUnemployedState = remember { mutableStateOf<List<Unemployed>?>(null) }

    val allUnemployedObserver = Observer<List<Unemployed>> { allUnemployed ->
        allUnemployedState.value = allUnemployed
    }

    DisposableEffect(viewModel) {
        viewModel.allUnemployed.observeForever(allUnemployedObserver)

        onDispose {
            viewModel.allUnemployed.removeObserver(allUnemployedObserver)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        content = {
            allUnemployedState.value?.let {
                items(count = it.size) { index ->
                    UnemployedCard(unemployed = allUnemployedState.value!![index])
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    )
}