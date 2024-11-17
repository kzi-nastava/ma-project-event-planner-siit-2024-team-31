package com.example.ep2024.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//TODO: not final

@Parcelize
data class Event(
    val name: String,
    val description: String,
    val city: String,
    val location: String,
    val type: String // Open or Closed
) : Parcelable