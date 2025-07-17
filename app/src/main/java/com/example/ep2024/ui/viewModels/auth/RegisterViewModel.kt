package com.example.ep2024.ui.viewModels.auth

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.auth.RegisterParams
import com.example.domain.entity.user.Role
import com.example.domain.usecase.auth.RegisterUseCase
import com.example.domain.util.Resource
import com.example.ep2024.ui.viewModels.auth.types.RegisterEvent
import com.example.ep2024.ui.viewModels.auth.types.RegisterFormState
import com.example.ep2024.ui.viewModels.auth.types.RegisterResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterFormState())
    val state = _state.asStateFlow()

    private val _registerResult = Channel<RegisterResult>()
    val registerResult = _registerResult.receiveAsFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EmailChanged -> _state.update { it.copy(email = event.value) }
            is RegisterEvent.PasswordChanged -> _state.update { it.copy(password = event.value) }
            is RegisterEvent.ConfirmPasswordChanged -> _state.update { it.copy(confirmPassword = event.value) }
            is RegisterEvent.RoleChanged -> _state.update { it.copy(role = event.value) }
            is RegisterEvent.FirstNameChanged -> _state.update { it.copy(firstName = event.value) }
            is RegisterEvent.LastNameChanged -> _state.update { it.copy(lastName = event.value) }
            is RegisterEvent.PhoneNumberChanged -> _state.update { it.copy(phoneNumber = event.value) }
            is RegisterEvent.CompanyNameChanged -> _state.update { it.copy(companyName = event.value) }
            is RegisterEvent.CompanyPhoneNumberChanged -> _state.update { it.copy(phoneNumber = event.value) }
            is RegisterEvent.DescriptionChanged -> _state.update { it.copy(description = event.value) }
            is RegisterEvent.CountryChanged -> _state.update { it.copy(country = event.value) }
            is RegisterEvent.CityChanged -> _state.update { it.copy(city = event.value) }
            is RegisterEvent.AddressChanged -> _state.update { it.copy(address = event.value) }
            is RegisterEvent.ZipCodeChanged -> _state.update { it.copy(zipCode = event.value) }
            is RegisterEvent.ProfileImagePicked -> {
                _state.update { it.copy(photos = event.uri?.let { listOf(it) } ?: emptyList()) }
            }
            is RegisterEvent.PortfolioImagesPicked -> {
                _state.update { it.copy(photos = event.uris) }
            }
            RegisterEvent.Register -> register()
            RegisterEvent.ClearError -> _state.update { it.copy(error = null) }
            RegisterEvent.ClearState -> _state.value = RegisterFormState()
        }
    }

    private fun register() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val currentState = _state.value

            // Map form data to RegisterParams according to DTO structure
            val params = RegisterParams(
                firstName = if (currentState.role == Role.PUP) currentState.companyName ?: "" else currentState.firstName,
                lastName = currentState.lastName,
                email = currentState.email,
                password = currentState.password,
                role = currentState.role,
                profileImageUri = null, // Not used in this API structure
                companyName = null, // Not used since we map it to firstName for PUP
                description = currentState.description,
                photos = currentState.photos.map { it.toString() },
                phoneNumber = currentState.phoneNumber,
                country = currentState.country,
                city = currentState.city,
                address = currentState.address,
                zipCode = currentState.zipCode
            )

            val result = registerUseCase(params)

            when (result) {
                is Resource.Success -> {
                    _registerResult.send(RegisterResult.Success)
                }
                is Resource.Error -> {
                    _registerResult.send(RegisterResult.Error(result.message ?: "Unknown error"))
                }
                is Resource.Loading -> {
                    // Handle loading if needed
                }
            }

            _state.update { it.copy(isLoading = false) }
        }
    }
}