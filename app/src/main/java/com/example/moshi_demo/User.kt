package com.example.moshi_demo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class User(
    val id: Int,
    val name: String,
    val email: String,

    @Json(name = "is_active")
    val isActive: Boolean,

    val age: Int,
    val registered: LocalDate
)
