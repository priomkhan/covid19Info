package com.priomkhan.covid_19info.whoRssApi

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.priomkhan.covid_19info.LOG_TAG
import com.priomkhan.covid_19info.whoRssData.WhoRSS
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class WhoRssRepository(val app: Application) {
    var whoRssFeeds = MutableLiveData<WhoRSS>()
    val busyBool = MutableLiveData<Boolean>()

    init {
        val RSS_link = WhoApiService.RSS_link
        val RSS_to_JSON_API = WhoApiService.RSS_to_JSON_API
        val url_get_data = StringBuilder(RSS_to_JSON_API)
        url_get_data.append(RSS_link)
        Log.i(LOG_TAG, "calling getdata with url ${url_get_data.toString()}")
        getData(url_get_data.toString())

    }

    fun getData(url: String) = runBlocking { // this: CoroutineScope
        var rssString = ""
        val job = CoroutineScope(Dispatchers.IO).launch {

            rssString = loadRss(url)
            if (rssString.isNotBlank()) {

                Log.i(LOG_TAG, "Task from nested launch: String ${rssString.length}")
                callWhoWebService(rssString)
            }

        }
        val queue = job.join()
        Log.i(LOG_TAG, "Getting RSS Coroutine Done: ${queue} \n")

    }


    @WorkerThread
    suspend fun callWhoWebService(data: String) {
        if (networkAvailable()) {

            Log.i(LOG_TAG, "Network Available: Loading RSS Data....")

            val moshi = Moshi.Builder().build()
            val adapter: JsonAdapter<WhoRSS> = moshi.adapter(WhoRSS::class.java)
            val rssData = adapter.fromJson(data)

            if (rssData != null) {

                try {
                    Log.i(LOG_TAG, "RSS DATA HEADER ${rssData.rss.channel[0].title[0].toString()}")

                    whoRssFeeds.postValue(rssData)

                } catch (e: Exception) {
                    Log.i(LOG_TAG, "Exception....${e}}")
                }
            }

        }
    }


    @WorkerThread
    suspend fun loadRss(url: String): String {
        var stream = ""
        if (networkAvailable()) {
            try {
                val url = URL(url)
                val urlConnection = url.openConnection() as HttpURLConnection
                if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream: InputStream = BufferedInputStream(urlConnection.inputStream)
                    val r = BufferedReader(InputStreamReader(inputStream))
                    val sb = java.lang.StringBuilder()
                    var line: String? = ""
                    while (r.readLine().also { line = it } != null) {
                        sb.append(line)
                        stream = sb.toString()
                    }
                    urlConnection.disconnect()
                }
            } catch (ex: java.lang.Exception) {
                Log.i(LOG_TAG, "GetHttpDataHandler : $ex")

            }
        }

        return stream
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