package com.priomkhan.covid_19info.data

import com.squareup.moshi.Json

data class Covid19Info(
    @Json(name = "Global")
    val Global: Global,
    @Json(name = "Countries")
    val Countries: List<Countries>,
    @Json(name = "Date")
    val Date: String
)