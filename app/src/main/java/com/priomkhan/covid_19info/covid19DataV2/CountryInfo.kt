package com.priomkhan.covid_19info.covid19DataV2

import com.squareup.moshi.Json

data class CountryInfo(
    @Json(name = "lat") val lat: Double,
    @Json(name = "long") val long: Double,
    @Json(name = "flag") val flag: String
)