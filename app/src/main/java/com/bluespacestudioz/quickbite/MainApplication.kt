package com.bluespacestudioz.quickbite

import android.app.Application
import com.bluespacestudioz.quickbite.data.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    val firebaseAuth : FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    val fireStore : FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    val authRepository : AuthRepository by lazy {
        AuthRepository(firebaseAuth,fireStore)
    }

}