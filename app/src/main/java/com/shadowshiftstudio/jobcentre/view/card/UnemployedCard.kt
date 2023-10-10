package com.shadowshiftstudio.jobcentre.view.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shadowshiftstudio.jobcentre.model.entity.Unemployed
import com.shadowshiftstudio.jobcentre.view.app.theme.md_theme_dark_background
import com.shadowshiftstudio.jobcentre.view.app.theme.md_theme_light_background

@Composable
fun UnemployedCard(unemployed: Unemployed) {

    val experienceText = when (val experience = unemployed.workExperience) {
        1 -> "1 год"
        in 2..4 -> "$experience года"
        in 5..20 -> "$experience лет"
        else -> "Более 20 лет" // Или любое другое желаемое форматирование
    }

    val age = when (val age = unemployed.age) {
        1 -> "1 год"
        in 2..4, in 22..24, in 32..34, in 42..44 , in 52..54 , in 62..64 -> "$age года"
        in 5..20, in 25..30, in 35..40, in 45..50 , in 55..60 -> "$age лет"
        else -> "$age год" // Или любое другое желаемое форматирование
    }

    Card(
        modifier = Modifier
            .clickable { }
            .fillMaxWidth()
//        colors = CardColors(
//            md_theme_light_background,
//            md_theme_light_background,
//            md_theme_light_background,
//            md_theme_light_background
//        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = unemployed.fullName,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = unemployed.speciality,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Опыт работы: $experienceText",
                color = Color.Black
            )
            Text(
                text = "Возраст: $age",
                color = Color.Black
            )
        }
    }
}