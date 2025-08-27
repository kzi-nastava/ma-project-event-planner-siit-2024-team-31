package com.example.ep2024.ui.viewModels.auth.types;

sealed interface LoginEvent {
    data class EmailChanged(val value: String) : LoginEvent
    data class PasswordChanged(val value: String) : LoginEvent
    object Login : LoginEvent
    object ClearError : LoginEvent
    object ClearState : LoginEvent

}
