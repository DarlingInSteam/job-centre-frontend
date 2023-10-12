package com.shadowshiftstudio.jobcentre.app.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shadowshiftstudio.jobcentre.app.employer.view_model.HomeViewModel
import com.shadowshiftstudio.jobcentre.domain.model.entity.Ability
import com.shadowshiftstudio.jobcentre.domain.model.entity.Unemployed
import com.shadowshiftstudio.jobcentre.domain.model.enum.getRussianEducationLevel

@Composable
fun UnemployedCard(unemployed: Unemployed, navController: NavController, viewModel: HomeViewModel) {

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

    Card(
        modifier = Modifier
            .clickable {
                viewModel.unemployedFullScreen = unemployed
                navController.navigate("unemployed_screen")
            }
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = unemployed.fullName,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = unemployed.speciality,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Опыт работы: $experienceText",
            )
            Text(
                text = "Возраст: $ageText",
            )
            Text(
                text = "Уровень образования: ${getRussianEducationLevel(unemployed.educationLevel)}",
            )
            Text(
                text = "Образовательное учреждение: ${unemployed.educationalInstitution}",
            )

            Spacer(modifier = Modifier.height(5.dp))

            Abilities(abilities = unemployed.abilities)
        }
    }
}

@Composable
fun Abilities(abilities: List<Ability>) {
    var currentRow by remember { mutableStateOf(0) }
    var expended by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ChipVerticalGrid(
            spacing = 7.dp,
            modifier = Modifier,
            onRowChange = { newCurrentRow ->
                currentRow = newCurrentRow
                if (currentRow == 2) expended = true
            }
        ) {
            abilities.forEach { word ->
                Text(
                    word.text,
                    modifier = Modifier
                        .clickable { },
                    textDecoration = TextDecoration.Underline,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun ChipVerticalGrid(
    modifier: Modifier = Modifier,
    spacing: Dp,
    onRowChange: (Int) -> Unit,
    content: @Composable () -> Unit,
) {
    var currentRow = 0

    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        var currentOrigin = IntOffset.Zero
        val spacingValue = spacing.toPx().toInt()
        val placeables = measurables.map { measurable ->
            val placeable = measurable.measure(constraints)

            if (currentOrigin.x > 0f && currentOrigin.x + placeable.width > constraints.maxWidth) {
                currentRow += 1
                onRowChange(currentRow)

                currentOrigin =
                    currentOrigin.copy(x = 0, y = currentOrigin.y + placeable.height + spacingValue)
            }

            placeable to currentOrigin.also {
                currentOrigin = it.copy(x = it.x + placeable.width + spacingValue)
            }
        }


        layout(
            width = constraints.maxWidth,
            height = placeables.lastOrNull()?.run { first.height + second.y } ?: 0
        ) {
            placeables.forEach {
                val (placeable, origin) = it
                placeable.place(origin.x, origin.y)
            }
        }
    }
}