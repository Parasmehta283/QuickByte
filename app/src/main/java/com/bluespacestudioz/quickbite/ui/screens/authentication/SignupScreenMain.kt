package com.bluespacestudioz.quickbite.ui.screens.authentication

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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bluespacestudioz.quickbite.R
import com.bluespacestudioz.quickbite.data.AuthRepository
import com.bluespacestudioz.quickbite.navigation.Routes
import com.bluespacestudioz.quickbite.ui.theme.LoginBrandText
import com.bluespacestudioz.quickbite.ui.theme.LoginError
import com.bluespacestudioz.quickbite.ui.theme.LoginFieldIcon
import com.bluespacestudioz.quickbite.ui.theme.LoginGray
import com.bluespacestudioz.quickbite.ui.theme.LoginInk
import com.bluespacestudioz.quickbite.ui.theme.LoginOrange
import com.bluespacestudioz.quickbite.ui.theme.LoginTagline
import com.bluespacestudioz.quickbite.ui.theme.QuickBiteSurface
import com.bluespacestudioz.quickbite.ui.theme.SignupFieldBackground
import com.bluespacestudioz.quickbite.ui.theme.SignupFieldBorder

@Composable
fun SignupScreenMain(authRepository: AuthRepository, navController: NavHostController) {
    val factory = AuthViewModelFactory(authRepository)
    val authViewModel: AuthViewModel = viewModel(factory = factory)
    val uiState by authViewModel.signupUiState.collectAsStateWithLifecycle()
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            navController.navigate(Routes.LOGIN) {
                popUpTo(Routes.SIGNUP) { inclusive = true }
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
            .padding(top = 40.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignupScreenTop()
        SignupScreenCredentials(
            uiState = uiState,
            onNameChange = { authViewModel.onSignupNameChange(it) },
            onEmailChange = { authViewModel.onSignupEmailChange(it) },
            onPasswordChange = { authViewModel.onSignupPasswordChange(it) },
            onConfirmPasswordChange = { authViewModel.onSignupConfirmPasswordChange(it) },
            isPasswordVisible = isPasswordVisible,
            isConfirmPasswordVisible = isConfirmPasswordVisible,
            onTogglePasswordVisibility = { isPasswordVisible = !isPasswordVisible },
            onConfirmPasswordVisibility = { isConfirmPasswordVisible = !isConfirmPasswordVisible }
        )
        SignupScreenBottomBar(
            uiState = uiState,
            onSignupButtonClick = { authViewModel.onSignupButtonClick() },
            navController = navController
        )
    }
}

@Composable
fun SignupScreenTop() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.signup_cloche),
            contentDescription = null,
            modifier = Modifier.size(84.dp)
        )
        Spacer(Modifier.height(4.dp))
        Row {
            Text("Quick ", color = LoginBrandText, fontSize = 36.sp, lineHeight = 44.sp, fontWeight = FontWeight.Bold)
            Text("Bite", color = LoginOrange, fontSize = 36.sp, lineHeight = 44.sp, fontWeight = FontWeight.Bold)
        }
        Text("Good Food. Anytime.", color = LoginTagline, fontSize = 16.sp, lineHeight = 24.sp)
        Spacer(Modifier.height(40.dp))
        Text("Create Account", color = LoginInk, fontSize = 30.sp, lineHeight = 38.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(4.dp))
        Text("Join Quick Bite", color = LoginTagline, fontSize = 18.sp, lineHeight = 26.sp)
    }
}

@Composable
fun SignupScreenCredentials(
    uiState: SignUpUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    isConfirmPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    onConfirmPasswordVisibility: () -> Unit
) {
    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = LoginInk,
        unfocusedTextColor = LoginInk,
        focusedContainerColor = SignupFieldBackground,
        unfocusedContainerColor = SignupFieldBackground,
        focusedBorderColor = LoginOrange,
        unfocusedBorderColor = SignupFieldBorder,
        focusedLeadingIconColor = LoginFieldIcon,
        unfocusedLeadingIconColor = LoginFieldIcon,
        focusedTrailingIconColor = LoginFieldIcon,
        unfocusedTrailingIconColor = LoginFieldIcon,
        focusedPlaceholderColor = LoginGray,
        unfocusedPlaceholderColor = LoginGray,
        cursorColor = LoginOrange
    )
    val fieldModifier = Modifier.fillMaxWidth().height(56.dp)
    val fieldShape = RoundedCornerShape(12.dp)
    val fieldTextStyle = TextStyle(fontSize = 17.sp)

    Column(Modifier.padding(top = 16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        QuickBiteTextField(
            value = uiState.name,
            onValueChange = onNameChange,
            label = "Name",
            leadingIcon = Icons.Outlined.PersonOutline,
            visualTransformation = false,
            usePlaceholder = true,
            colors = fieldColors,
            shape = fieldShape,
            textStyle = fieldTextStyle,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
            modifier = fieldModifier
        )
        QuickBiteTextField(
            value = uiState.email,
            onValueChange = onEmailChange,
            label = "Email",
            leadingIcon = Icons.Outlined.Email,
            visualTransformation = false,
            usePlaceholder = true,
            colors = fieldColors,
            shape = fieldShape,
            textStyle = fieldTextStyle,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            modifier = fieldModifier
        )
        QuickBiteTextField(
            value = uiState.password,
            onValueChange = onPasswordChange,
            label = "Password",
            leadingIcon = Icons.Outlined.Lock,
            trailingIcon = if (isPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
            trailingContentDescription = if (isPasswordVisible) "Hide password" else "Show password",
            onTrailingIconClick = onTogglePasswordVisibility,
            visualTransformation = !isPasswordVisible,
            usePlaceholder = true,
            colors = fieldColors,
            shape = fieldShape,
            textStyle = fieldTextStyle,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
            modifier = fieldModifier
        )
        QuickBiteTextField(
            value = uiState.confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = "Confirm Password",
            leadingIcon = Icons.Outlined.Lock,
            trailingIcon = if (isConfirmPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
            trailingContentDescription = if (isConfirmPasswordVisible) "Hide confirm password" else "Show confirm password",
            onTrailingIconClick = onConfirmPasswordVisibility,
            visualTransformation = !isConfirmPasswordVisible,
            usePlaceholder = true,
            colors = fieldColors,
            shape = fieldShape,
            textStyle = fieldTextStyle,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            modifier = fieldModifier
        )
        uiState.errorMessage?.let {
            Text(it, color = LoginError, fontSize = 14.sp, lineHeight = 20.sp, modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

@Composable
fun SignupScreenBottomBar(
    uiState: SignUpUiState,
    onSignupButtonClick: () -> Unit,
    navController: NavHostController
) {
    Column(
        modifier = Modifier.padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QuickBiteButton(
            onButtonClick = onSignupButtonClick,
            label = "Sign Up",
            isLoading = uiState.isLoading,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = LoginOrange, contentColor = QuickBiteSurface),
            textStyle = TextStyle(fontSize = 19.sp, fontWeight = FontWeight.Medium),
            loadingColor = QuickBiteSurface,
            modifier = Modifier.height(56.dp)
        )
        Spacer(Modifier.height(18.dp))
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Text("Already have an account?", color = LoginTagline, fontSize = 14.sp)
            TextButton(
                onClick = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.SIGNUP) { inclusive = true }
                    }
                },
                colors = ButtonDefaults.textButtonColors(contentColor = LoginOrange)
            ) {
                Text("Login", fontSize = 14.sp)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 393, heightDp = 820)
@Composable
private fun SignupScreenPreview() {
    Column(
        Modifier.fillMaxSize().background(QuickBiteSurface).verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp).padding(top = 40.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignupScreenTop()
        SignupScreenCredentials(SignUpUiState(), {}, {}, {}, {}, false, false, {}, {})
        SignupScreenBottomBar(SignUpUiState(), {}, rememberNavController())
    }
}
