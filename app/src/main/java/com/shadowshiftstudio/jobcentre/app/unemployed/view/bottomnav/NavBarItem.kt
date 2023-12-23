package com.shadowshiftstudio.jobcentre.app.unemployed.view.bottomnav

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
object ConstantsUnemployed {
    val BottomNavItems = listOf(
        NavBarItem(
            title = "Главная",
            icon = Icons.Default.Home,
            route = "home"
        ),
        NavBarItem(
            title = "Мои резюме",
            icon = Icons.Filled.Info,
            route = "resume"
        ),
        NavBarItem(
            title = "Отклики",
            icon = Icons.Default.ListAlt,
            route = "applies"
        ),
        NavBarItem(
            title = "Профиль",
            icon = Icons.Default.AccountCircle,
            route = "profile"
        )
    )
}