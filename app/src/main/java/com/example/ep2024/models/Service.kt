package com.example.ep2024.models

import com.example.ep2024.utils.service.ConfirmationMethod
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.UUID

//TODO: not final

@Parcelize
data class Service(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val characteristics: String,
    val price: Double,
    val discount: Double,
    val images: @RawValue List<String>,
    val eventTypes: @RawValue List<String>,
    val categoryId: String,
    val visibility: Boolean,
    val availability: Boolean,
    val bookingDuration: Int?,
    val minParticipation: Int?,
    val maxParticipation: Int?,
    val bookingPeriod: Int,
    val cancellationPeriod: Int,
    val confirmationMethod: ConfirmationMethod,
    val isDeleted: Boolean = false
) : Parcelable