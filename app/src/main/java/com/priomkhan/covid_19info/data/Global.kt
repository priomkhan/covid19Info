package com.priomkhan.covid_19info.data

import com.squareup.moshi.Json

data class Global(
    @Json(name = "NewConfirmed")
    val NewConfirmed: Long,
    @Json(name = "TotalConfirmed")
    val TotalConfirmed: Long,
    @Json(name = "NewDeaths")
    val NewDeaths: Long,
    @Json(name = "TotalDeaths")
    val TotalDeaths: Long,
    @Json(name = "NewRecovered")
    val NewRecovered: Long,
    @Json(name = "TotalRecovered")
    val TotalRecovered: Long
)