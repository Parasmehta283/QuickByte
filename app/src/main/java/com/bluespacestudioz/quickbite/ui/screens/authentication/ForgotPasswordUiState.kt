package com.bluespacestudioz.quickbite.ui.screens.authentication

data class ForgotPasswordUiState(
    val email : String = "",
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val isSuccess : Boolean = false
)
