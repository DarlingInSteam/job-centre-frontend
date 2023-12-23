package com.shadowshiftstudio.jobcentre.app.employer.view.main_screens

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
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shadowshiftstudio.jobcentre.domain.model.entity.Unemployed
import com.shadowshiftstudio.jobcentre.app.card.UnemployedCard
import com.shadowshiftstudio.jobcentre.app.employer.view.unemployed_screens.UnemployedScreen
import com.shadowshiftstudio.jobcentre.app.employer.view_model.HomeViewModel

@Composable
fun Home(
    viewModel: HomeViewModel
) {
    val navController = rememberNavController()

    LaunchedEffect(viewModel) {
        viewModel.getAllUnemployed()
    }
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(23.dp),
            ) {
                HomeButtons({}, {})
                Spacer(modifier = Modifier.height(20.dp))
                UnemployedList(viewModel = viewModel, navController)
            }
        }
        composable("unemployed_screen") {
            UnemployedScreen(navController, viewModel.unemployedFullScreen, viewModel)
        }
    }
}

@Composable
fun HomeButtons(
    changeButtonSheetSortVisible: () -> Unit,
    changeButtonSheetFilterVisible: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ExtendedFloatingActionButton(
            onClick = { /*TODO*/
                changeButtonSheetSortVisible()
            },
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .width(146.dp)
                .height(40.dp),
        ) {
            Icon(
                Icons.Default.Sort,
                "Sort icon",
                modifier = Modifier
                    .width(18.dp)
                    .height(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Сортировка")
        }
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
    }
}

@Composable
fun UnemployedList(viewModel: HomeViewModel, navController: NavController) {
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
                    UnemployedCard(unemployed = allUnemployedState.value!![index], navController, viewModel)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    )
}