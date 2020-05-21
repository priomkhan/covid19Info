package com.priomkhan.covid_19info.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.priomkhan.covid_19info.covid19ApiV2.RepositoryV2
import com.priomkhan.covid_19info.covid19DataV2.CountriesV2

class HomeViewModel(app: Application) : AndroidViewModel(app) {
    //private val dataRepo = Covid19Repository(app)

    //val covid19Data = dataRepo.covid19Data

    //val covid19CountryData = dataRepo.covid19CountryData


    private val dataRepoV2 = RepositoryV2(app)
    val covid19GlobalData = dataRepoV2.globalData
    val covid19CountryData = dataRepoV2.countryData
    val covid19CountryHistoricalData = dataRepoV2.countryHistoricalData
    val CountryProvinceData = dataRepoV2.provinceData
    val busyBool = dataRepoV2.busyBool
    val selectedCountry = MutableLiveData<CountriesV2>()


    fun getCountryInfo(countryName: String) {
        if (countryName.isNotBlank()) {
            dataRepoV2.getCountryInfo(countryName)
        }

    }

    fun getCountryHistoricalInfo(countryName: String) {
        if (countryName.isNotBlank()) {
            dataRepoV2.getCountryHistoricalInfo(countryName)
        }

    }

    fun getCountryProvinceInfo(countryName: String) {
        if (countryName.isNotBlank()) {
            dataRepoV2.getCountryProvinceInfo(countryName)
        }

    }
}
