package com.priomkhan.covid_19info.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.priomkhan.covid_19info.whoRssApi.WhoRssRepository

class DashboardViewModel(app: Application) : AndroidViewModel(app) {
    private val whoDataRepo = WhoRssRepository(app)
    val whoRssFeeds = whoDataRepo.whoRssFeeds
}