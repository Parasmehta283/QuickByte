package com.bluespacestudioz.quickbite.ui.screens.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bluespacestudioz.quickbite.data.AuthRepository
import com.bluespacestudioz.quickbite.model.User
import com.bluespacestudioz.quickbite.model.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    private val _signupUiState = MutableStateFlow(SignUpUiState())
    private val _forgotUiState = MutableStateFlow(ForgotPasswordUiState())
    val loginUiState = _loginUiState.asStateFlow()
    val signupUiState = _signupUiState.asStateFlow()
    val forgotUiState = _forgotUiState.asStateFlow()

    fun onLoginEmailChange(email: String) {
        _loginUiState.update { it.copy(email = email) }
    }

    fun onLoginPasswordChange(password: String) {
        _loginUiState.update { it.copy(password = password) }
    }

    fun onSignupNameChange(name : String)
    {
        _signupUiState.update { it.copy(name = name) }
    }
    fun onSignupEmailChange(email: String) {
        _signupUiState.update { it.copy(email = email) }
    }

    fun onSignupPasswordChange(password: String) {
        _signupUiState.update { it.copy(password = password) }
    }

    fun onSignupConfirmPasswordChange(confirmPassword: String) {
        _signupUiState.update { it.copy(confirmPassword = confirmPassword) }
    }

    fun onPasswordResetEmailChange(email: String)
    {
        _forgotUiState.update { it.copy(email = email) }
    }

    fun onPasswordResetButtonClick()
    {
        if (_forgotUiState.value.email.isBlank())
        {
            _forgotUiState.update {
                it.copy(errorMessage = "Email can't be empty.")
            }
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(_forgotUiState.value.email).matches()){
            _forgotUiState.update {
                it.copy(errorMessage = "Invalid email format.")
            }
            return
        }

        viewModelScope.launch {
            _forgotUiState.update { it.copy(isLoading = true) }
            val result = authRepository.passwordResetLink(_forgotUiState.value.email)

            result.onSuccess {
                _forgotUiState.update { it.copy(isLoading = false, isSuccess = true) }
            }

            result.onFailure { exception ->
                _forgotUiState.update { it.copy(isLoading = false, errorMessage = exception.message ?: "Failed to send email reset link") }
            }
        }
    }

//    fun validPasswordFormat(password: String)
//    {
//        if (password.length < 6)
//        {
//            _signupUiState.update { it.copy(errorMessage = "Password length must be greater then 6.") }
//            return
//        }
//
//        password.any { it.isDigit() }
//    }
    fun onSignupButtonClick(
    ) {
        val state = _signupUiState.value

        // Check for password and confirm password
            if (state.password != state.confirmPassword) {
                _signupUiState.update {
                    it.copy(
                        errorMessage = "Password and Confirmed Password are different.",
                    )
                }
                return
            }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches()){
            _signupUiState.update {
                it.copy(errorMessage = "Invalid email format.")
            }
            return
        }
        viewModelScope.launch {
            _signupUiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = authRepository.signup(name = state.name, email = state.email, password = state.password)

            result.onSuccess {
                _signupUiState.update {
                    it.copy(isLoading = false, isSuccess = true)
                }
            }
            result.onFailure { exception ->
                _signupUiState.update {
                    it.copy(isLoading = false, errorMessage = exception.message ?: "Signup Failed.")
                }
            }
        }
    }

    fun onLoginButtonClick()
    {
        val state = _loginUiState.value

        if(state.email.isBlank() || state.password.isBlank())
        {
            _loginUiState.update { it.copy(errorMessage = "Email or Password can't be blank.") }
            return
        }
        viewModelScope.launch {
            _loginUiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = authRepository.loginIn(state.email,state.password)

            result.onSuccess {
                val roleString = authRepository.getUserRole()
                val roleEnum = try {
                    roleString?.let { UserRole.valueOf(it) }
                } catch (e : Exception)
                {
                    UserRole.CUSTOMER
                }
                _loginUiState.update {
                    it.copy(isLoading = false, isSuccess = true, userRole = roleEnum)
                }
            }
            result.onFailure { exception ->
                _loginUiState.update {
                    it.copy(isLoading = false, errorMessage = exception.message ?: "Login Failed.")
                }
            }
        }
    }


}


class AuthViewModelFactory(private val authRepository: AuthRepository) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)){
            return AuthViewModel(authRepository = authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
    }
}