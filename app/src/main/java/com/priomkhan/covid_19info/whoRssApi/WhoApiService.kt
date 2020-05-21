package com.priomkhan.covid_19info.whoRssApi

import com.priomkhan.covid_19info.whoRssData.Rss
import retrofit2.Response
import retrofit2.http.GET

//Credits:
// WHO (World Health Organization) : News Feeds
//Project: WHO news via RSS : https://www.who.int/rss-feeds/news-english.xml
//RSS to JSON Converter by Simon Breiter
//Project : RSS to JSON : https://rsstojson.com/
interface WhoApiService {
    @GET("/rss-feeds/news-english.xml")
    suspend fun getRss(): Response<Rss>

    companion object {
        const val API_URL = "https://www.who.int/"

        const val RSS_link = "https://www.who.int/rss-feeds/news-english.xml"
        const val RSS_to_JSON_API = "https://rsstojson.com/v1/api/?rss_url="
    }
}