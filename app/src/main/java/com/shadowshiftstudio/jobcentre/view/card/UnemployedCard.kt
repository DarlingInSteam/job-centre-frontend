package com.shadowshiftstudio.jobcentre.view.card

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.shadowshiftstudio.jobcentre.model.entity.Unemployed
import com.shadowshiftstudio.jobcentre.view.app.theme.md_theme_dark_background

@Composable
fun UnemployedCard(unemployed: Unemployed) {
    Card(
        modifier = Modifier
            .clickable { }
            .fillMaxWidth(),
        colors = CardColors(
            md_theme_dark_background,
            md_theme_dark_background,
            md_theme_dark_background,
            md_theme_dark_background
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = unemployed.fullName, color = Color.White)
            Text(text = unemployed.age.toString(), color = Color.White)
            Text(text = unemployed.workExperience.toString(), color = Color.White)
            Text(text = unemployed.speciality, color = Color.White)
            Text(text = unemployed.registrationDate.toString(), color = Color.White)
            Text(text = unemployed.educationLevel.toString(), color = Color.White)
            Text(text = unemployed.educationDocumentData, color = Color.White)
            Text(text = unemployed.educationalInstitution, color = Color.White)
        }
    }
}