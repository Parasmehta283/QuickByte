package com.bluespacestudioz.quickbite.ui.screens.authentication

import com.bluespacestudioz.quickbite.model.User
import com.bluespacestudioz.quickbite.model.UserRole

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
    val userRole : UserRole? = null
)
