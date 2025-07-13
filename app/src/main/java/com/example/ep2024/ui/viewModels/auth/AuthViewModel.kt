package com.example.ep2024.ui.viewModels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.auth.LoginUseCase
import com.example.domain.usecase.auth.RegisterUseCase
import com.example.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

data class RegisterState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.update { it.copy(isLoading = true, isSuccess = false, error = null) }
            when (val result = loginUseCase(email, password)) {
                is Resource.Success -> {
                    _loginState.update { it.copy(isLoading = false, isSuccess = true) }
                }
                is Resource.Error -> {
                    _loginState.update { it.copy(isLoading = false, error = result.message) }
                }
                is Resource.Loading -> {  }
            }
        }
    }

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            _registerState.update { it.copy(isLoading = true, isSuccess = false, error = null) }
            when (val result = registerUseCase(username, email, password)) {
                is Resource.Success -> {
                    _registerState.update { it.copy(isLoading = false, isSuccess = true) }

                }
                is Resource.Error -> {
                    _registerState.update { it.copy(isLoading = false, error = result.message) }
                }
                is Resource.Loading -> {  }
            }
        }
    }

    fun clearLoginError() {
        _loginState.update { it.copy(error = null) }
    }

    fun clearRegisterState() {
        _registerState.value = RegisterState()
    }
}