package com.example.moshi_demo

import android.os.Build
import androidx.annotation.RequiresApi
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate

class LocalDateAdapter {

    @RequiresApi(Build.VERSION_CODES.O)
    @FromJson
    fun fromJson(date: String): LocalDate {
        return LocalDate.parse(date)
    }

    @ToJson
    fun toJson(date: LocalDate): String {
        return date.toString()
    }
}
