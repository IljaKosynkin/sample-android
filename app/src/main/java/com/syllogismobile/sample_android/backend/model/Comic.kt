package com.syllogismobile.sample_android.backend.model

import android.support.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class Comic(
    @Json(name = "safe_title") val title: String,
    @Json(name = "img") val image: String,
    @Json(name = "alt") val description: String
)