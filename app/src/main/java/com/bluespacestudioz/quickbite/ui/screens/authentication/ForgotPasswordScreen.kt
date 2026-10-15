package com.bluespacestudioz.quickbite.ui.screens.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bluespacestudioz.quickbite.R
import com.bluespacestudioz.quickbite.data.AuthRepository
import com.bluespacestudioz.quickbite.ui.theme.ForgotPasswordAccent
import com.bluespacestudioz.quickbite.ui.theme.LoginBrandText
import com.bluespacestudioz.quickbite.ui.theme.LoginError
import com.bluespacestudioz.quickbite.ui.theme.LoginFieldBorder
import com.bluespacestudioz.quickbite.ui.theme.LoginGray
import com.bluespacestudioz.quickbite.ui.theme.LoginInk
import com.bluespacestudioz.quickbite.ui.theme.QuickBiteSurface

@Composable
fun ForgotPasswordMain(authRepository: AuthRepository, navController: NavHostController) {
    val factory = AuthViewModelFactory(authRepository = authRepository)
    val authViewModel: AuthViewModel = viewModel(factory = factory)
    val uiState by authViewModel.forgotUiState.collectAsStateWithLifecycle()

    Box(Modifier.fillMaxSize().background(QuickBiteSurface)) {
        Image(
            painterResource(R.drawable.forgot_password_background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column(
            Modifier.fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .imePadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginScreenTopBar(navController)
            ForgotPasswordBrand()
            ForgotPasswordMainBody(
                uiState = uiState,
                onEmailChange = { authViewModel.onPasswordResetEmailChange(it) },
                onPasswordResetClick = { authViewModel.onPasswordResetButtonClick() }
            )
        }
    }
}

@Composable
fun LoginScreenTopBar(navController: NavHostController) {
    Row(Modifier.fillMaxWidth().padding(start = 8.dp, top = 4.dp)) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = LoginInk)
        }
    }
}

@Composable
private fun ForgotPasswordBrand() {
    Image(painterResource(R.drawable.signup_cloche), contentDescription = null, modifier = Modifier.size(64.dp))
    Spacer(Modifier.height(4.dp))
    Row {
        Text("Quick ", color = LoginBrandText, fontSize = 24.sp, lineHeight = 30.sp, fontWeight = FontWeight.Bold)
        Text("Bite", color = ForgotPasswordAccent, fontSize = 24.sp, lineHeight = 30.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ForgotPasswordMainBody(
    uiState: ForgotPasswordUiState,
    onEmailChange: (String) -> Unit,
    onPasswordResetClick: () -> Unit
) {
    Column(
        Modifier.fillMaxWidth().padding(horizontal = 22.dp).padding(top = 48.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ForgotPasswordHeader()
        Spacer(Modifier.height(34.dp))
        ForgotPasswordForm(uiState, onEmailChange, onPasswordResetClick)
        Spacer(Modifier.height(40.dp))
        Image(
            painterResource(R.drawable.forgot_password_envelope),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
    }
}

@Composable
private fun ForgotPasswordHeader() {
    Text(
        "Forgot Password?", color = LoginInk, fontSize = 28.sp, lineHeight = 36.sp,
        fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
    )
    Spacer(Modifier.height(10.dp))
    Text(
        "No worries! Enter your email address and\nwe’ll send you a link to reset your password.",
        color = LoginGray, fontSize = 15.sp, lineHeight = 22.sp, textAlign = TextAlign.Center
    )
}

@Composable
private fun ForgotPasswordForm(
    uiState: ForgotPasswordUiState,
    onEmailChange: (String) -> Unit,
    onPasswordResetClick: () -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        QuickBiteTextField(
            value = uiState.email,
            onValueChange = onEmailChange,
            label = "Enter your email address",
            leadingIcon = Icons.Outlined.Email,
            usePlaceholder = true,
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            textStyle = TextStyle(fontSize = 14.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = LoginInk,
                unfocusedTextColor = LoginInk,
                focusedContainerColor = QuickBiteSurface,
                unfocusedContainerColor = QuickBiteSurface,
                focusedBorderColor = ForgotPasswordAccent,
                unfocusedBorderColor = LoginFieldBorder,
                focusedLeadingIconColor = LoginGray,
                unfocusedLeadingIconColor = LoginGray,
                focusedPlaceholderColor = LoginGray,
                unfocusedPlaceholderColor = LoginGray,
                cursorColor = ForgotPasswordAccent
            ),
            modifier = Modifier.height(56.dp)
        )
        uiState.errorMessage?.let {
            Text(
                it, color = LoginError, fontSize = 14.sp, lineHeight = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
        Spacer(Modifier.height(28.dp))
        QuickBiteButton(
            onButtonClick = onPasswordResetClick,
            label = "Send Reset Link",
            isLoading = uiState.isLoading,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ForgotPasswordAccent, contentColor = QuickBiteSurface),
            textStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
            loadingColor = QuickBiteSurface,
            modifier = Modifier.height(52.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 393, heightDp = 800)
@Composable
private fun ForgotPasswordPreview() {
    Box(Modifier.fillMaxSize().background(QuickBiteSurface)) {
        Image(
            painterResource(R.drawable.forgot_password_background), contentDescription = null,
            contentScale = ContentScale.FillBounds, modifier = Modifier.matchParentSize()
        )
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
            LoginScreenTopBar(rememberNavController())
            ForgotPasswordBrand()
            ForgotPasswordMainBody(ForgotPasswordUiState(), {}, {})
        }
    }
}
