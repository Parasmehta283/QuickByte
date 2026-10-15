package com.bluespacestudioz.quickbite.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bluespacestudioz.quickbite.data.AuthRepository
import com.bluespacestudioz.quickbite.model.UserRole
import com.bluespacestudioz.quickbite.ui.screens.authentication.ForgotPasswordMain
import com.bluespacestudioz.quickbite.ui.screens.authentication.LoadingBar
import com.bluespacestudioz.quickbite.ui.screens.authentication.LoginScreenMain
import com.bluespacestudioz.quickbite.ui.screens.authentication.SignupScreenMain

@Composable
fun AppNavigation(authRepository: AuthRepository)
{
    val navController = rememberNavController()

    val startDestination by produceState<String?>(initialValue = null) {
        val userRole = authRepository.getUserRole()

        value = when (userRole) {
            "CUSTOMER" -> Routes.CUSTOMER_HOME
            "ADMIN" -> Routes.ADMIN_HOME
            "STAFF" -> Routes.STAFF_HOME
            else  -> Routes.LOGIN
        }
    }
    if (startDestination == null){
        LoadingBar()
    }else {
        NavHost(navController = navController, startDestination = startDestination!!)
        {
            composable(Routes.LOGIN) {
                LoginScreenMain(authRepository, navController)
            }
            composable(Routes.SIGNUP) {
                SignupScreenMain(authRepository, navController)
            }
            composable(Routes.FORGOT_PASSWORD) { ForgotPasswordMain(authRepository, navController) }
            composable(Routes.CUSTOMER_HOME) { }
            composable(Routes.STAFF_HOME) { }
            composable(Routes.ADMIN_HOME) { }

        }
    }
}