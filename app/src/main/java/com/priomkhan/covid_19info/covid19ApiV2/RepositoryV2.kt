package com.priomkhan.covid_19info.covid19ApiV2

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.priomkhan.covid_19info.LOG_TAG
import com.priomkhan.covid_19info.covid19DataV2.CountriesV2
import com.priomkhan.covid_19info.covid19DataV2.CountryHistorical
import com.priomkhan.covid_19info.covid19DataV2.CountryProvinceInfo
import com.priomkhan.covid_19info.covid19DataV2.GlobalV2
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RepositoryV2(val app: Application) {

    val globalData = MutableLiveData<GlobalV2>()
    val countryData = MutableLiveData<CountriesV2>()
    val countryHistoricalData = MutableLiveData<CountryHistorical>()
    val provinceData = MutableLiveData<List<CountryProvinceInfo>>()
    val busyBool = MutableLiveData<Boolean>()
    val busyBool2 = MutableLiveData<Boolean>()
    val busyBool3 = MutableLiveData<Boolean>()

    init {

        CoroutineScope(Dispatchers.IO).launch {
            callWebService()
        }


    }

    fun getCountryInfo(countryName: String) = runBlocking {

        launch {
            busyBool.postValue(true)
            Log.i(LOG_TAG, "Task from runBlocking to get Country result")

            //delay(200L)
        }

        coroutineScope {
            launch {

                Log.i(LOG_TAG, "Task from nested launch to get $countryName data")
                callWebService(countryName)
                //delay(200L)
            }

        }

        //delay(200L)
        Log.i(
            LOG_TAG,
            "getData() Coroutine scope is over for Country Result"
        ) // This line is not printed until the nested launch completes
        busyBool.postValue(false)
    }


    fun getCountryHistoricalInfo(countryName: String) = runBlocking {

        launch {
            busyBool2.postValue(true)
            Log.i(LOG_TAG, "Task from runBlocking to get Historical result")

            //delay(200L)
        }

        coroutineScope {
            launch {

                Log.i(LOG_TAG, "Task from nested launch to get $countryName data")
                callWebServiceHistorical(countryName)
                //delay(200L)
            }

        }

        //delay(200L)
        Log.i(
            LOG_TAG,
            "getData() Coroutine scope is over For Historical result"
        ) // This line is not printed until the nested launch completes
        busyBool2.postValue(false)
    }

    fun getCountryProvinceInfo(countryName: String) = runBlocking {

        launch {
            busyBool3.postValue(true)
            Log.i(LOG_TAG, "Task from runBlocking to get Province result")

            //delay(200L)
        }

        coroutineScope {
            launch {

                Log.i(LOG_TAG, "Task from nested launch to get $countryName province data")
                callWebServiceProvince(countryName)
                //delay(200L)
            }

        }

        //delay(200L)
        Log.i(
            LOG_TAG,
            "getData() Coroutine scope is over For Historical result"
        ) // This line is not printed until the nested launch completes
        busyBool3.postValue(false)
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
                .baseUrl(ApiServiceV2.API_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi)) //we used moshi builder to map the json to class property
                .build()

            val service = retrofit.create(ApiServiceV2::class.java)


            try {
                val serviceDataReq = service.getAll()

                if (serviceDataReq.isSuccessful) {
                    val serviceData = serviceDataReq.body()

                    //Covid19Info(0,"Not Found", CountryInfo(0,"Not Found", "Not Found",0.0,0.0,"N/A"), 0, 0, 0, 0,0,0,0,0,0,0,0)
                    if (serviceData != null) {
                        globalData.postValue(serviceData)
                    }

                } else {
                    Log.i(LOG_TAG, "Data Error.... ${serviceDataReq.errorBody()}")
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

            Log.i(LOG_TAG, "Network Available: Getting Data for country: $countryName....")

            //If annotation used in Monster Data Class
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(ApiServiceV2.API_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi)) //we used moshi builder to map the json to class property
                .build()

            val service = retrofit.create(ApiServiceV2::class.java)



            try {
                val serviceDataReq = service.getCountry(countryName)
                if (serviceDataReq.isSuccessful) {
                    val serviceData = serviceDataReq.body()
                    if (serviceData != null) {
                        countryData.postValue(serviceData)
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
    suspend fun callWebServiceHistorical(countryName: String) {
        if (networkAvailable()) {

            Log.i(
                LOG_TAG,
                "Network Available: Getting Historical Data for country: $countryName...."
            )

            //If annotation used in Monster Data Class
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(ApiServiceV2.API_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi)) //we used moshi builder to map the json to class property
                .build()
//            val retrofit = Retrofit.Builder()
//                .baseUrl(ApiServiceV2.API_URL)
//                .addConverterFactory(MoshiConverterFactory.create(moshi)) //we used moshi builder to map the json to class property
//                .build()

            val service = retrofit.create(ApiServiceV2::class.java)
            try {
                val serviceDataReq = service.getHistorical(countryName)
                if (serviceDataReq.isSuccessful) {
                    val serviceData = serviceDataReq.body()
                    if (serviceData != null) {

                        Log.i(
                            LOG_TAG,
                            "Historical Data: ${serviceData.country} : ${serviceData.timeline.cases}"
                        )
                        countryHistoricalData.postValue(serviceData)
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
    suspend fun callWebServiceProvince(countryName: String) {
        if (networkAvailable()) {

            Log.i(
                LOG_TAG,
                "Network Available: Getting Province Data for country: $countryName...."
            )

            //If annotation used in Monster Data Class
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(ApiServiceV2.API_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi)) //we used moshi builder to map the json to class property
                .build()
//            val retrofit = Retrofit.Builder()
//                .baseUrl(ApiServiceV2.API_URL)
//                .addConverterFactory(MoshiConverterFactory.create(moshi)) //we used moshi builder to map the json to class property
//                .build()

            val service = retrofit.create(ApiServiceV2::class.java)
            try {
                val serviceDataReq = service.getCountryProvince(countryName)
                if (serviceDataReq.isSuccessful) {
                    val serviceDataList = serviceDataReq.body()
                    if (!serviceDataList.isNullOrEmpty()) {

                        Log.i(
                            LOG_TAG,
                            "Number of Province: ${serviceDataList.size}"
                        )
                        provinceData.postValue(serviceDataList)
                    }else{
                        provinceData.postValue(emptyList())
                    }

                } else {
                    Log.i(LOG_TAG, "Data Error.... ${serviceDataReq.errorBody().toString()}")
                }
            } catch (e: Exception) {
                Log.i(LOG_TAG, "Exception....${e}}")
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