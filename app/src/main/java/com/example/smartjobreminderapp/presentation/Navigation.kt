package com.example.smartjobreminderapp.presentation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartjobreminderapp.presentation.home.HomeScreen
import com.example.smartjobreminderapp.presentation.insert.InsertScreen

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController,
        enterTransition = {
            slideInHorizontally(initialOffsetX = { 0 }) + fadeIn()
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -1 }) + fadeOut()
        },
        startDestination = Screen.Home){
        composable<Screen.Home> {
            HomeScreen(navController)
        }

        composable<Screen.Insert> {
            InsertScreen(navController)
        }
    }

}