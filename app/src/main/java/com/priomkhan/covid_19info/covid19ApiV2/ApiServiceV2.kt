package com.priomkhan.covid_19info.covid19ApiV2

import com.priomkhan.covid_19info.covid19DataV2.CountriesV2
import com.priomkhan.covid_19info.covid19DataV2.CountryProvinceInfo
import com.priomkhan.covid_19info.covid19DataV2.CountryHistorical
import com.priomkhan.covid_19info.covid19DataV2.GlobalV2
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

//Credit:
//Covid-19 Global, Country and Historical Data
//Project : Novel COVID API : https://corona.lmao.ninja/

interface ApiServiceV2 {
    companion object {
        const val API_URL = "https://disease.sh/"
    }


    @GET("/v2/all")
    suspend fun getAll(): Response<GlobalV2>

    @GET("/v2/countries/{country}")
    suspend fun getCountry(@Path("country") countryName: String): Response<CountriesV2>

    @GET("/v2/historical/{country}")
    suspend fun getHistorical(@Path("country") countryName: String): Response<CountryHistorical>

    @GET("/v2/gov/{country}")
    suspend fun getCountryProvince(@Path("country") countryName: String): Response<List<CountryProvinceInfo>>

}