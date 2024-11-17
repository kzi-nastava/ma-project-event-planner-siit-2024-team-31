package com.example.ep2024.utils.service

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class ConfirmationMethod : Parcelable {
    AUTOMATIC,
    MANUAL
}