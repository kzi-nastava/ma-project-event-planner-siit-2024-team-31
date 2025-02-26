package com.example.ep2024.utils.service

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChangeEntry(
    val fieldName: String,
    val oldValue: String?,
    val newValue: String?
) : Parcelable