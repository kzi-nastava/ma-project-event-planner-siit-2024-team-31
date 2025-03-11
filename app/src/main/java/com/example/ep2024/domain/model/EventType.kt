package com.example.ep2024.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//TODO: not final

@Parcelize
data class EventType(
    val id: String,
    val name: String
) : Parcelable