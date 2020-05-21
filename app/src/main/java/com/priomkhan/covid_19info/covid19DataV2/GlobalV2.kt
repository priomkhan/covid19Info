package com.priomkhan.covid_19info.covid19DataV2

import com.squareup.moshi.Json


data class GlobalV2(

    @Json(name = "updated") val updated: Long,
    @Json(name = "cases") val cases: Long,
    @Json(name = "todayCases") val todayCases: Long,
    @Json(name = "deaths") val deaths: Long,
    @Json(name = "todayDeaths") val todayDeaths: Long,
    @Json(name = "recovered") val recovered: Long,
    @Json(name = "active") val active: Long,
    @Json(name = "critical") val critical: Long,
    @Json(name = "casesPerOneMillion") val casesPerOneMillion: Double,
    @Json(name = "deathsPerOneMillion") val deathsPerOneMillion: Double,
    @Json(name = "tests") val tests: Long,
    @Json(name = "testsPerOneMillion") val testsPerOneMillion: Double,
    @Json(name = "affectedCountries") val affectedCountries: Int


)