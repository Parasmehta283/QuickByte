package com.bluespacestudioz.quickbite.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation()
{
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LOGIN)
    {
        composable(Routes.LOGIN) {  }
        composable(Routes.SIGNUP) {  }
        composable(Routes.HOME) {  }
    }
}