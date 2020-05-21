package com.priomkhan.covid_19info.covid19DataV2

import com.squareup.moshi.Json

data class TimeLineData(
    @Json(name = "cases") val cases: Map<String, Int>,
    @Json(name = "deaths") val deaths: Map<String, Int>,
    @Json(name = "recovered") val recovered: Map<String, Int>
)
