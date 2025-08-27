package com.example.ep2024.ui.compose.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ep2024.ui.compose.components.textFields.FormTextField
import com.example.ep2024.ui.compose.components.screenHeaders.DefaultScreenHeader
import com.example.ep2024.ui.viewModels.auth.LoginViewModel
import com.example.ep2024.ui.viewModels.auth.types.LoginEvent
import com.example.ep2024.ui.viewModels.auth.types.LoginFormState
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onSuccess: () -> Unit,
    onGoToRegister: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.loginState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) onSuccess()
    }
    LaunchedEffect(state.error) {
        state.error?.let { msg ->
            scope.launch { snackbarHostState.showSnackbar(msg) }
            viewModel.clearLoginError()
        }
    }

    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            DefaultScreenHeader(
                title = "Welcome Back",
                text = "Login to your account",
            )
            LoginFormSection(
                state = state,
                onEvent = viewModel::onEvent,
                onGoToRegister = onGoToRegister
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
        SnackbarHost(snackbarHostState, Modifier.align(Alignment.BottomCenter))
    }
}


@Composable
private fun LoginFormSection(
    state: LoginFormState,
    onEvent: (LoginEvent) -> Unit,
    onGoToRegister: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FormTextField(
                value = state.email,
                label = "Email",
                onValueChange = { onEvent(LoginEvent.EmailChanged(it)) }
            )
            Spacer(Modifier.height(12.dp))
            FormTextField(
                value = state.password,
                label = "Password",
                password = true,
                onValueChange = { onEvent(LoginEvent.PasswordChanged(it)) }
            )
            Spacer(Modifier.height(20.dp))
            val gradient = Brush.horizontalGradient(
                listOf(MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.secondary))
            Button(
                onClick = { onEvent(LoginEvent.Login) },
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(gradient, shape = MaterialTheme.shapes.medium)
            ) {
                Text("Login")
            }
            TextButton(
                onClick = onGoToRegister,
                enabled = !state.isLoading
            ) { Text("Don't have an account?") }
        }
    }
}