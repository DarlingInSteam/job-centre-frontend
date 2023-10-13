package com.shadowshiftstudio.jobcentre.app.employer.view.unemployed_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.shadowshiftstudio.jobcentre.domain.model.entity.Unemployed
import com.shadowshiftstudio.jobcentre.domain.model.enum.getRussianEducationLevel

@Composable
fun UnemployedScreen(
    navController: NavController,
    unemployed: Unemployed
) {
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

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Default.ArrowBack, "", modifier = Modifier
                        .height(28.dp)
                        .width(28.dp)
                )
            }
            Text(text = "Настройки профиля", fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 23.dp, end = 23.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = unemployed.fullName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = unemployed.speciality,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Опыт работы: $experienceText",
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Возраст: $ageText",
                )
            }
            AsyncImage(
                model = "https://www.meme-arsenal.com/memes/1f1f9fa0355dfb137689834296f58467.jpg",
                contentDescription = "Photo",
                modifier = Modifier
                    .size(height = 120.dp, width = 80.dp),
                contentScale = ContentScale.Crop,
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 23.dp, end = 23.dp)
        ) {
            Text(text = "Образование", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Уровень образования: ${getRussianEducationLevel(unemployed.educationLevel)}",
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Образовательное учреждение: ${unemployed.educationalInstitution}",
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Год окончания учреждения: ${unemployed.educationDocumentData}"
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

        }
    }
}