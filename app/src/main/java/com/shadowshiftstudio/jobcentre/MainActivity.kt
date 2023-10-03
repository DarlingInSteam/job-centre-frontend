package com.shadowshiftstudio.jobcentre

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shadowshiftstudio.jobcentre.data.app.secure_data.SecureStore
import com.shadowshiftstudio.jobcentre.view.app.authentication.view.AuthorizationScreen
import com.shadowshiftstudio.jobcentre.view.app.theme.JobCentreTheme
import com.shadowshiftstudio.jobcentre.view.app.theme.md_theme_dark_background
import com.shadowshiftstudio.jobcentre.view.employer.view.bottomnav.ConstantsEmployer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecureStore.initialize(LocalContext.current)

            JobCentreTheme(dynamicColor = false, darkTheme = true) {
                val navController = rememberNavController()

                Surface {
                    AuthorizationScreen(navController = navController) {

                    }
                }
            }
        }
    }
}

@Composable
fun NavHostContainerEmployer(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
) {
    NavHost(
        navController = navHostController,
        startDestination = "home",
        modifier = Modifier.padding(paddingValues = paddingValues),

        builder = {
            composable("home") {

            }
        }
    )
}

@Composable
fun BottomNavigationBarEmployer(navHostController: NavHostController) {
    NavigationBar(
        containerColor = md_theme_dark_background
    ) {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()

        val currentRoute = navBackStackEntry?.destination?.route

        ConstantsEmployer.BottomNavItems.forEach { navItem ->

            NavigationBarItem(
                selected = currentRoute == navItem.route,

                onClick = {
                    navHostController.navigate(navItem.route)
                },

                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = navItem.title)
                },

                label = {
                    Text(text = navItem.title)
                },

                alwaysShowLabel = true
            )
        }
    }
}