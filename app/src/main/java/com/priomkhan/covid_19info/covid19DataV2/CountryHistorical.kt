package com.priomkhan.covid_19info.covid19DataV2

import com.squareup.moshi.Json

data class CountryHistorical(
    @Json(name = "country") val country: String,
    @Json(name = "province") val provinces: List<String>,
    @Json(name = "timeline") val timeline: TimeLineData
)