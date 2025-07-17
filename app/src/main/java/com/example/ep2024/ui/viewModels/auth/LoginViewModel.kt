package com.example.ep2024.ui.viewModels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.auth.LoginUseCase
import com.example.domain.util.Resource
import com.example.ep2024.ui.viewModels.auth.types.LoginFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginFormState())
    val loginState = _loginState.asStateFlow()

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
                is Resource.Loading -> { /* Optional: Handle loading state if needed */ }
            }
        }
    }

    fun clearLoginError() {
        _loginState.update { it.copy(error = null) }
    }
}