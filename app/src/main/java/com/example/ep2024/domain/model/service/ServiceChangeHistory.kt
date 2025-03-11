package com.example.ep2024.domain.model.service

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date
import java.util.UUID

@Parcelize
data class ServiceChangeHistory(
    val id: String = UUID.randomUUID().toString(),
    val serviceId: String,
    val changedBy: String,
    val changeDate: Date,
    val changes: List<ChangeEntry>
) : Parcelable
