package com.priomkhan.covid_19info.api

import com.priomkhan.covid_19info.data.Covid19Info
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/summary")
    suspend fun getAll(): Response<Covid19Info>

    @GET("/countries?sort=country")
    suspend fun getCountries(): Response<List<Covid19Info>>

    @GET("/countries/Bangladesh")
    suspend fun getCountry(): Response<Covid19Info>

    companion object {
        const val API_URL = "https://api.covid19api.com/"
    }
}