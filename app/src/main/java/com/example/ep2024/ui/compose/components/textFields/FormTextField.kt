package com.example.ep2024.ui.compose.components.textFields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun FormTextField(
    value: String,
    label: String,
    password: Boolean = false,
    onValueChange: (String) -> Unit,
    corderRadius: Dp = 12.dp
) {
    val visualTransformation = if (password) PasswordVisualTransformation() else VisualTransformation.None
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(corderRadius),
        visualTransformation = visualTransformation
    )
}