package com.bluespacestudioz.quickbite.ui.screens.authentication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bluespacestudioz.quickbite.R
import com.bluespacestudioz.quickbite.data.AuthRepository
import com.bluespacestudioz.quickbite.model.UserRole
import com.bluespacestudioz.quickbite.navigation.Routes
import com.bluespacestudioz.quickbite.ui.theme.LoginOrange
import com.bluespacestudioz.quickbite.ui.theme.LoginInk
import com.bluespacestudioz.quickbite.ui.theme.LoginGray
import com.bluespacestudioz.quickbite.ui.theme.LoginBrandText
import com.bluespacestudioz.quickbite.ui.theme.LoginTagline
import com.bluespacestudioz.quickbite.ui.theme.LoginFieldBackground
import com.bluespacestudioz.quickbite.ui.theme.LoginFieldBorder
import com.bluespacestudioz.quickbite.ui.theme.LoginFieldIcon
import com.bluespacestudioz.quickbite.ui.theme.LoginError
import com.bluespacestudioz.quickbite.ui.theme.LoginDivider
import com.bluespacestudioz.quickbite.ui.theme.QuickBiteSurface

@Composable
fun LoginScreenMain(authRepository: AuthRepository, navController: NavHostController) {
    val factory = AuthViewModelFactory(authRepository = authRepository)
    val authViewModel: AuthViewModel = viewModel(factory = factory)
    val uiState by authViewModel.loginUiState.collectAsStateWithLifecycle()
    var isPasswordHidden by remember { mutableStateOf(false) }

    val userRole : UserRole? = uiState.userRole

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess && uiState.userRole != null) {
            val destination = when(uiState.userRole)
            {
                UserRole.CUSTOMER -> Routes.CUSTOMER_HOME
                UserRole.STAFF -> Routes.STAFF_HOME
                else -> Routes.ADMIN_HOME
            }
            navController.navigate(destination) {
                popUpTo(Routes.LOGIN) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(QuickBiteSurface)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .padding(top = 56.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginScreenTop()
        LoginForm(
            uiState = uiState,
            onEmailChange = { authViewModel.onLoginEmailChange(it) },
            onPasswordChange = { authViewModel.onLoginPasswordChange(it) },
            isPasswordHidden = isPasswordHidden,
            onPasswordHidden = { isPasswordHidden = !isPasswordHidden },
            onLoginButtonClick = { authViewModel.onLoginButtonClick() },
            onForgotPasswordClick = { navController.navigate(Routes.FORGOT_PASSWORD) },
            onSignUpClick = { navController.navigate(Routes.SIGNUP) }
        )
    }
}

@Composable
fun LoginScreenTop() {
    Image(
        painter = painterResource(R.drawable.login_burger),
        contentDescription = null,
        modifier = Modifier.size(88.dp)
    )
    Spacer(Modifier.height(4.dp))
    Row {
        Text(
            "Quick ", color = LoginBrandText, fontSize = 40.sp,
            fontWeight = FontWeight.Bold, fontFamily = FontFamily.SansSerif
        )
        Text(
            "Bite", color = LoginOrange, fontSize = 40.sp,
            fontWeight = FontWeight.Bold, fontFamily = FontFamily.SansSerif
        )
    }
    Text("Good Food. Anytime.", color = LoginTagline, fontSize = 17.sp)
}

@Composable
private fun LoginForm(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    isPasswordHidden: Boolean,
    onPasswordHidden: () -> Unit,
    onLoginButtonClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Welcome Back", color = LoginInk, fontSize = 32.sp,
            fontWeight = FontWeight.Bold, lineHeight = 40.sp
        )
        Spacer(Modifier.height(4.dp))
        Text("Login to continue", color = LoginGray, fontSize = 18.sp)
        Spacer(Modifier.height(22.dp))
        LoginScreenTextFields(
            uiState, onEmailChange, onPasswordChange, isPasswordHidden, onPasswordHidden
        )
        LoginActions(uiState.errorMessage, uiState.isLoading, onLoginButtonClick, onForgotPasswordClick)
        Spacer(Modifier.height(18.dp))
        LoginGoogleSignIn()
        Spacer(Modifier.height(18.dp))
        LoginSignUpPrompt(onSignUpClick)
    }
}

@Composable
fun LoginScreenTextFields(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    isPasswordHidden: Boolean,
    onPasswordHidden: () -> Unit
) {
    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = LoginInk,
        unfocusedTextColor = LoginInk,
        focusedContainerColor = LoginFieldBackground,
        unfocusedContainerColor = LoginFieldBackground,
        focusedBorderColor = LoginOrange,
        unfocusedBorderColor = LoginFieldBorder,
        focusedLeadingIconColor = LoginFieldIcon,
        unfocusedLeadingIconColor = LoginFieldIcon,
        focusedTrailingIconColor = LoginFieldIcon,
        unfocusedTrailingIconColor = LoginFieldIcon,
        focusedPlaceholderColor = LoginGray,
        unfocusedPlaceholderColor = LoginGray,
        cursorColor = LoginOrange
    )
    Column {
        QuickBiteTextField(
            value = uiState.email,
            onValueChange = onEmailChange,
            label = "Email",
            leadingIcon = Icons.Outlined.Email,
            usePlaceholder = true,
            colors = fieldColors,
            shape = RoundedCornerShape(10.dp),
            textStyle = TextStyle(fontSize = 17.sp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            modifier = Modifier.height(56.dp)
        )
        Spacer(Modifier.height(12.dp))
        QuickBiteTextField(
            value = uiState.password,
            onValueChange = onPasswordChange,
            label = "Password",
            leadingIcon = Icons.Outlined.Lock,
            trailingIcon = if (!isPasswordHidden) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
            trailingContentDescription = if (!isPasswordHidden) "Show password" else "Hide password",
            onTrailingIconClick = onPasswordHidden,
            visualTransformation = isPasswordHidden,
            usePlaceholder = true,
            colors = fieldColors,
            shape = RoundedCornerShape(10.dp),
            textStyle = TextStyle(fontSize = 17.sp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            modifier = Modifier.height(56.dp)
        )
    }
}

@Composable
private fun LoginActions(
    errorMessage: String?,
    isLoading: Boolean,
    onLoginButtonClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        TextButton(
            onClick = onForgotPasswordClick,
            modifier = Modifier.align(Alignment.End),
            colors = ButtonDefaults.textButtonColors(contentColor = LoginOrange),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
        ) {
            Text("Forgot Password?", fontSize = 14.sp)
        }
        errorMessage?.let {
            Text(
                text = it,
                color = LoginError,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
            )
        }
        Spacer(Modifier.height(4.dp))
        Button(
            onClick = onLoginButtonClick,
            enabled = !isLoading,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = LoginOrange, contentColor = QuickBiteSurface),
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(Modifier.size(24.dp), color = QuickBiteSurface, strokeWidth = 2.dp)
            } else {
                Text("Login", fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
private fun LoginGoogleSignIn() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            HorizontalDivider(Modifier.weight(1f), color = LoginDivider)
            Text("or", color = LoginGray, fontSize = 16.sp, modifier = Modifier.padding(horizontal = 20.dp))
            HorizontalDivider(Modifier.weight(1f), color = LoginDivider)
        }
        Spacer(Modifier.height(18.dp))
        // shortcut: Google sign-in has no existing handler, enable when authentication supports it.
        OutlinedButton(
            onClick = {},
            enabled = false,
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, LoginDivider),
            colors = ButtonDefaults.outlinedButtonColors(disabledContentColor = LoginInk),
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Image(painterResource(R.drawable.login_google), contentDescription = null, modifier = Modifier.size(26.dp))
            Spacer(Modifier.width(18.dp))
            Text("Continue with Google", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun LoginSignUpPrompt(onSignUpClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        Text("Don’t have an account?", color = LoginGray, fontSize = 14.sp)
        TextButton(
            onClick = onSignUpClick,
            colors = ButtonDefaults.textButtonColors(contentColor = LoginOrange)
        ) {
            Text("Sign Up", fontSize = 14.sp)
        }
    }
}

@Preview(showBackground = true, widthDp = 393, heightDp = 800)
@Composable
private fun LoginScreenPreview() {
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp).padding(top = 56.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginScreenTop()
        LoginForm(LoginUiState(), {}, {}, false, {}, {}, {}, {})
    }
}
