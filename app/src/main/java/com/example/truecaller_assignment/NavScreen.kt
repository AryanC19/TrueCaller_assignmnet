package com.example.avengers_tracker.android

import HomeViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.truecaller_assignment.Screens.HomeScreen
import com.example.truecaller_assignment.Screens.InfoDisplayScreen
import com.example.truecaller_assignment.Screens.ManualRegistrationScreen

@Composable
fun NavScreen() {
    val navController = rememberNavController()
    val viewModel: HomeViewModel = viewModel()
    val userData by viewModel.userData.collectAsState()

    NavHost(navController = navController, startDestination = "/home") {
        composable(route = "/home") {
            HomeScreen(
                navController = navController,
                viewModel = viewModel,
                onNavigateToProfile = {
                    userData?.let {
                        navController.navigate(
                            "infoDisplayScreen/${it.firstName}/${it.lastName}/${it.phoneNumber}"
                        )
                    }
                }
            )
        }

        composable(route = "/manual_registration") {
            ManualRegistrationScreen(navController)
        }

        composable(
            "infoDisplayScreen/{firstName}/{lastName}/{mobileNumber}",
            arguments = listOf(
                navArgument("firstName") { type = NavType.StringType },
                navArgument("lastName") { type = NavType.StringType },
                navArgument("mobileNumber") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val firstName = backStackEntry.arguments?.getString("firstName") ?: ""
            val lastName = backStackEntry.arguments?.getString("lastName") ?: ""
            val mobileNumber = backStackEntry.arguments?.getString("mobileNumber") ?: ""
            InfoDisplayScreen(
                firstName = firstName,
                lastName = lastName,
                mobileNumber = mobileNumber,
                navController = navController
            )
        }
    }
}