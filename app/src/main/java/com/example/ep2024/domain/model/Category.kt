package com.example.ep2024.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

//TODO: not final
@Parcelize
data class Category(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val isActive: Boolean = true
) : Parcelable