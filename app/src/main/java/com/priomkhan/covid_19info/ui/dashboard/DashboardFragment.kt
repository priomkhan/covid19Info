package com.priomkhan.covid_19info.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.priomkhan.covid_19info.LOG_TAG
import com.priomkhan.covid_19info.R
import com.priomkhan.covid_19info.whoRssData.Channel

class DashboardFragment : Fragment(), RssRecyclerAdapter.GridItemListener {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var rssRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(requireActivity()).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val rssRecyclerView: RecyclerView = root.findViewById(R.id.rssRecycleView)

        dashboardViewModel.whoRssFeeds.observe(requireActivity(), Observer {
            Log.i(LOG_TAG, "Rss Data Change.....................")
            try {
                val adapter: RssRecyclerAdapter = RssRecyclerAdapter(requireContext(), it, this)
                rssRecyclerView.adapter = adapter
                rssRecyclerView.adapter?.notifyDataSetChanged()
            } catch (e: Exception) {
                Log.i(LOG_TAG, "Exception: $e")
            }


        })

        return root
    }

    override fun onGridItemClick(rssChannel: Channel) {
        TODO("Not yet implemented")
    }

}
