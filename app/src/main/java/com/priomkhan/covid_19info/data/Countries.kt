package com.priomkhan.covid_19info.data

import com.squareup.moshi.Json;

data class Countries(
    @Json(name = "Country") val country: String,
    @Json(name = "CountryCode") val countryCode: String,
    @Json(name = "Slug") val slug: String,
    @Json(name = "NewConfirmed") val newConfirmed: Int,
    @Json(name = "TotalConfirmed") val totalConfirmed: Int,
    @Json(name = "NewDeaths") val newDeaths: Int,
    @Json(name = "TotalDeaths") val totalDeaths: Int,
    @Json(name = "NewRecovered") val newRecovered: Int,
    @Json(name = "TotalRecovered") val totalRecovered: Int,
    @Json(name = "Date") val date: String
)