package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun BlinkaApp(initialShowBreak: Boolean = false) {
    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()

    val startDestination = if (initialShowBreak) "break" else "dashboard"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("dashboard") {
            DashboardScreen(
                viewModel = viewModel,
                onTestBreak = {
                    navController.navigate("break")
                }
            )
        }
        composable("break") {
            BreakScreen(
                onFinished = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    } else {
                        // If opened directly from notification
                        navController.navigate("dashboard") {
                            popUpTo("break") { inclusive = true }
                        }
                    }
                },
                onRecordBreak = {
                    viewModel.recordBreak()
                }
            )
        }
    }
}
