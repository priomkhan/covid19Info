package com.priomkhan.covid_19info.covid19DataV2

import com.squareup.moshi.Json

data class CountriesV2(
    @Json(name = "country") val country: String,
    @Json(name = "countryInfo") val countryInfo: CountryInfo,
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
    @Json(name = "testsPerOneMillion") val testsPerOneMillion: Double

)