package com.example.ep2024.ui.viewModels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ep2024.ui.viewModels.auth.types.LoginEvent
import com.example.ep2024.ui.viewModels.auth.types.LoginFormState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginState = MutableStateFlow(LoginFormState())
    val loginState = _loginState.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged ->
                _loginState.update { it.copy(email = event.value, error = null) }
            is LoginEvent.PasswordChanged ->
                _loginState.update { it.copy(password = event.value, error = null) }
            LoginEvent.Login -> login()
            LoginEvent.ClearError -> clearLoginError()
            LoginEvent.ClearState -> LoginFormState()
        }
    }

    private fun login() {
        val current = _loginState.value
        if (current.email.isBlank() || current.password.isBlank()) {
            _loginState.update { it.copy(error = "Fill all fields") }
            return
        }
        viewModelScope.launch {
            _loginState.update { it.copy(isLoading = true, error = null) }
            // TODO: call repository / API
            // Simulate success:
            _loginState.update { it.copy(isLoading = false, isSuccess = true) }
        }
    }

    fun clearLoginError() {
        _loginState.update { it.copy(error = null) }
    }
}