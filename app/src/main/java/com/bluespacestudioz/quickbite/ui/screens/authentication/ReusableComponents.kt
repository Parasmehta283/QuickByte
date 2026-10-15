package com.bluespacestudioz.quickbite.ui.screens.authentication

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.LocalTextStyle
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun QuickBiteTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    leadingContentDescription: String? = null,
    trailingContentDescription: String? = null,
    visualTransformation : Boolean = false,
    onTrailingIconClick : (() -> Unit)? = null,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    usePlaceholder: Boolean = false,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    shape: Shape = OutlinedTextFieldDefaults.shape,
    textStyle: TextStyle = LocalTextStyle.current,
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default

) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = if (usePlaceholder) null else { { Text(label) } },
        placeholder = if (usePlaceholder) { { Text(label) } } else null,
        colors = colors,
        shape = shape,
        textStyle = textStyle,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        leadingIcon = {
            leadingIcon?.let {
                Icon(imageVector = leadingIcon, contentDescription = leadingContentDescription)
            }
        },
        trailingIcon = {

            trailingIcon?.let {
                IconButton(onClick = { onTrailingIconClick?.invoke() })
                {
                    Icon(imageVector = trailingIcon, contentDescription = trailingContentDescription)
                }
            }
        },
        visualTransformation = if (visualTransformation) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun QuickBiteButton(
    modifier: Modifier = Modifier,
    label: String,
    onButtonClick: () -> Unit,
    isLoading: Boolean,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    loadingColor: Color = MaterialTheme.colorScheme.primary
) {
    Button(
        onClick = onButtonClick,
        modifier = modifier.fillMaxWidth(),
        enabled = !isLoading,
        shape = shape,
        colors = colors
    )
    {
        if (isLoading) {
            LoadingBar(color = loadingColor)
        } else {
            Text(label, style = textStyle)
        }
    }
}

@Composable
fun LoadingBar(color: Color = MaterialTheme.colorScheme.primary) {
    CircularProgressIndicator(
        modifier = Modifier.size(24.dp),
        strokeWidth = 2.dp,
        color = color
    )
}
