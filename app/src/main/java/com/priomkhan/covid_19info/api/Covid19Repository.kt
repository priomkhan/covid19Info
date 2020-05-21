package com.priomkhan.covid_19info.api

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.priomkhan.covid_19info.LOG_TAG
import com.priomkhan.covid_19info.data.Covid19Info
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class Covid19Repository(val app: Application) {

    val covid19Data = MutableLiveData<Covid19Info>()
    val covid19CountryData = MutableLiveData<Covid19Info>()

    init {

        CoroutineScope(Dispatchers.IO).launch {
            callWebService()
        }


    }

    fun getCountryInfo(countryName: String) = runBlocking {
        coroutineScope { // Creates a coroutine scope to get each user details
            launch {

                Log.i(LOG_TAG, "Task from nested launch to get each user details")
                //callWebService(countryName)
                //delay(200L)
            }

        }
    }

    /*
Function to make a web service call using Retrofit2
*/
    @WorkerThread
    suspend fun callWebService() {
        if (networkAvailable()) {

            Log.i(LOG_TAG, "Network Available: Getting Data....")

            //If annotation used in Monster Data Class
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(ApiService.API_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi)) //we used moshi builder to map the json to class property
                .build()

            val service = retrofit.create(ApiService::class.java)


            try {
                val serviceDataReq = service.getAll()

                if (serviceDataReq.isSuccessful) {
                    val serviceData = serviceDataReq.body()
                    //Covid19Info(0,"Not Found", CountryInfo(0,"Not Found", "Not Found",0.0,0.0,"N/A"), 0, 0, 0, 0,0,0,0,0,0,0,0)
                    if (serviceData != null) {
                        //Log.i(LOG_TAG,"Data..  ${serviceData[0]}")
                        covid19Data.postValue(serviceData)
                    }

                } else {
                    Log.i(LOG_TAG, "Data Error.... ${serviceDataReq.errorBody().toString()}")
                }
            } catch (e: Exception) {
                Log.i(LOG_TAG, "Exception....${e}}")
            }

        }
    }

    /*
Function to make a web service call using Retrofit2
*/
    @WorkerThread
    suspend fun callWebService(countryName: String) {
        if (networkAvailable()) {

            Log.i(LOG_TAG, "Network Available: Getting Data....")

            //If annotation used in Monster Data Class
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(ApiService.API_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi)) //we used moshi builder to map the json to class property
                .build()

            val service = retrofit.create(ApiService::class.java)



            try {
                val serviceDataReq = service.getCountry()
                if (serviceDataReq.isSuccessful) {
                    val serviceData = serviceDataReq.body()
                    if (serviceData != null) {
                        covid19CountryData.postValue(serviceData)
                    }

                } else {
                    Log.i(LOG_TAG, "Data Error.... ${serviceDataReq.errorBody().toString()}")
                }
            } catch (e: Exception) {
                Log.i(LOG_TAG, "Exception....${e.message}}")
            }


        }
    }

    //This function check if the mobile is connected to the Wifi or Mobile internet, however
// it does not work above Android O.
    @Suppress("DEPRECATION")
    private fun networkAvailable(): Boolean {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }

}