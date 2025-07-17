package com.example.ep2024.ui.viewModels.auth.types

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
    data class PhoneNumberChanged(val value: String) : RegisterEvent
    data class CountryChanged(val value: String) : RegisterEvent
    data class CityChanged(val value: String) : RegisterEvent
    data class AddressChanged(val value: String) : RegisterEvent
    data class ZipCodeChanged(val value: String) : RegisterEvent
    data class ConfirmPasswordChanged(val value: String) : RegisterEvent
    data class CompanyPhoneNumberChanged(val value: String) : RegisterEvent
    object Register : RegisterEvent
    object ClearError : RegisterEvent
    object ClearState : RegisterEvent
}