package com.example.ep2024.ui.viewModels.auth

import android.net.Uri
import com.example.domain.entity.user.Role

sealed interface RegisterEvent {
    data class FirstNameChanged(val value: String) : RegisterEvent
    data class LastNameChanged(val value: String) : RegisterEvent
    data class EmailChanged(val value: String) : RegisterEvent
    data class PasswordChanged(val value: String) : RegisterEvent
    data class RoleChanged(val value: Role) : RegisterEvent
    data class CompanyNameChanged(val value: String) : RegisterEvent
    data class DescriptionChanged(val value: String) : RegisterEvent
    data class ProfileImagePicked(val uri: Uri?) : RegisterEvent
    data class PortfolioImagesPicked(val uris: List<Uri>) : RegisterEvent
    object Register : RegisterEvent
    object ClearError : RegisterEvent
    object ClearState : RegisterEvent
}