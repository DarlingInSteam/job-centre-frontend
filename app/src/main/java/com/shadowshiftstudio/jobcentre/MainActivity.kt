package com.shadowshiftstudio.jobcentre

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shadowshiftstudio.jobcentre.data.app.secure_data.SecureStore
import com.shadowshiftstudio.jobcentre.app.app.authentication.view.AuthorizationScreen
import com.shadowshiftstudio.jobcentre.app.app.theme.JobCentreTheme
import com.shadowshiftstudio.jobcentre.app.app.theme.md_theme_light_background
import com.shadowshiftstudio.jobcentre.app.employer.view.bottomnav.ConstantsEmployer
import com.shadowshiftstudio.jobcentre.app.employer.view.main_screens.Home
import com.shadowshiftstudio.jobcentre.app.employer.view_model.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecureStore.initialize(LocalContext.current)

            JobCentreTheme(dynamicColor = true, darkTheme = true) {
                val navController = rememberNavController()
                var isAuthorization = false

                Surface {
                    var isLogin = SecureStore.getIsLogin()

                    if(isLogin == "1" || isAuthorization) {
                        Scaffold(
                            bottomBar = {
                                BottomNavigationBarEmployer(navHostController = navController)
                            },
                            content = { paddingValues ->
                                NavHostContainerEmployer(
                                    navHostController = navController,
                                    paddingValues = paddingValues
                                )
                            }
                        )
                    } else {
                        AuthorizationScreen(navController = navController) {
                            isAuthorization = true
                        }
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
    val homeViewModel = HomeViewModel(LocalContext.current)

    NavHost(
        navController = navHostController,
        startDestination = "home",
        modifier = Modifier.padding(paddingValues = paddingValues),

        builder = {
            composable("home") {
                Home(homeViewModel)
            }

            composable("company") {

            }

            composable("profile") {

            }

            composable("vacancies") {

            }
        }
    )
}

@Composable
fun BottomNavigationBarEmployer(navHostController: NavHostController) {
    NavigationBar(
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