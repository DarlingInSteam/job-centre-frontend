package com.shadowshiftstudio.jobcentre.view.employer.view.bottomnav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.ui.graphics.vector.ImageVector

data class NavBarItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
)
object ConstantsEmployer {
    val BottomNavItems = listOf(
        NavBarItem(
            title = "Главная",
            icon = Icons.Default.Home,
            route = "home"
        ),
        NavBarItem(
            title = "О компании",
            icon = Icons.Filled.Info,
            route = "company"
        ),
        NavBarItem(
            title = "Мои вакансии",
            icon = Icons.Default.ListAlt,
            route = "vacancies"
        ),
        NavBarItem(
            title = "Профиль",
            icon = Icons.Default.AccountCircle,
            route = "profile"
        )
    )
}