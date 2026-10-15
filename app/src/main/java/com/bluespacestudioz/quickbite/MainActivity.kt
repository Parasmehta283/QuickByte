package com.bluespacestudioz.quickbite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bluespacestudioz.quickbite.data.AuthRepository
import com.bluespacestudioz.quickbite.navigation.AppNavigation
import com.bluespacestudioz.quickbite.ui.theme.QuickBiteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val app = application as MainApplication
        val authRepository = app.authRepository
        val firebaseAuth = app.firebaseAuth
        val fireStore = app.fireStore
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickBiteTheme {
                AppNavigation(authRepository)
            }
        }
    }
}