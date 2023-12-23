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
import com.shadowshiftstudio.jobcentre.app.employer.view.bottomnav.ConstantsEmployer
import com.shadowshiftstudio.jobcentre.app.employer.view.main_screens.Company
import com.shadowshiftstudio.jobcentre.app.employer.view.main_screens.Home
import com.shadowshiftstudio.jobcentre.app.employer.view.main_screens.Profile
import com.shadowshiftstudio.jobcentre.app.employer.view.main_screens.Vacancies
import com.shadowshiftstudio.jobcentre.app.employer.view_model.CompanyViewModel
import com.shadowshiftstudio.jobcentre.app.employer.view_model.HomeViewModel
import com.shadowshiftstudio.jobcentre.app.employer.view_model.ProfileViewModel
import com.shadowshiftstudio.jobcentre.app.employer.view_model.VacanciesViewModel
import com.shadowshiftstudio.jobcentre.domain.model.enum.Role

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
                        if (SecureStore.getUserRole() == Role.EMPLOYED) {
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
                        }
                        else {
                            Scaffold(
                                bottomBar = {
                                    BottomNavigationBarUnemployed(navHostController = navController)
                                },
                                content = { paddingValues ->
                                    NavHostContainerUnemployed(
                                        navHostController = navController,
                                        paddingValues = paddingValues
                                    )
                                }
                            )
                        }
                    } else {
                        AuthorizationScreen(navController = navController) {
                            isAuthorization = true
                            isLogin = "1"
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NavHostContainerUnemployed(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
) {
    val homeViewModel = com.shadowshiftstudio.jobcentre.app.unemployed.view_model.HomeViewModel(
        LocalContext.current)

    NavHost(
        navController = navHostController,
        startDestination = "home",
        modifier = Modifier.padding(paddingValues = paddingValues),

        builder = {
            composable("home") {
                com.shadowshiftstudio.jobcentre.app.unemployed.view.main_screen.Home(homeViewModel = homeViewModel)
            }

            composable("resume") {

            }

            composable("applies") {

            }

            composable("profile") {

            }
        }
    )
}

@Composable
fun NavHostContainerEmployer(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
) {
    val homeViewModel = HomeViewModel(LocalContext.current)
    val companyViewModel = CompanyViewModel(LocalContext.current)
    val vacanciesViewModel = VacanciesViewModel(LocalContext.current)
    val profileViewModel = ProfileViewModel(LocalContext.current)

    NavHost(
        navController = navHostController,
        startDestination = "home",
        modifier = Modifier.padding(paddingValues = paddingValues),

        builder = {
            composable("home") {
                Home(homeViewModel)
            }

            composable("company") {
                Company(companyViewModel)
            }

            composable("profile") {
                Profile(profileViewModel)
            }

            composable("vacancies") {
                Vacancies(vacanciesViewModel)
            }
        }
    )
}

@Composable
fun BottomNavigationBarUnemployed(navHostController: NavHostController) {
    NavigationBar(
    ) {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()

        val currentRoute = navBackStackEntry?.destination?.route

        com.shadowshiftstudio.jobcentre.app.unemployed.view.bottomnav.ConstantsUnemployed.BottomNavItems.forEach { navItem ->

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