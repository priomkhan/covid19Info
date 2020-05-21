package com.priomkhan.covid_19info.covid19DataV2

import com.squareup.moshi.Json

class CountryProvinceInfo (@Json(name = "province") val province: String,
                           @Json(name = "cases") val cases: Long,
                           @Json(name = "probableCases") val probableCases: Long,
                           @Json(name = "deaths") val deaths: Long)

