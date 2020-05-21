package com.priomkhan.covid_19info.ui.dashboard

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.priomkhan.covid_19info.R
import com.priomkhan.covid_19info.whoRssData.Channel
import com.priomkhan.covid_19info.whoRssData.Item
import com.priomkhan.covid_19info.whoRssData.WhoRSS


class RssRecyclerAdapter(
    val context: Context,
    private val rssChannel: WhoRSS,
    private val itemListener: RssRecyclerAdapter.GridItemListener
) : RecyclerView.Adapter<RssRecyclerAdapter.ViewHolder>() {

    private val rssItemsList = ArrayList<Item>()

    init {
        rssItemsList.addAll(rssChannel.rss.channel[0].item)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val rssTxtItemTitle: TextView = itemView.findViewById(R.id.txtRssTitle)

        val rssTxtItemPubDate: TextView = itemView.findViewById(R.id.txtRssPubDate)

        val rssTxtItemContent: TextView = itemView.findViewById(R.id.txtRssContent)


    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RssRecyclerAdapter.ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context) //The parent is the ViewGroup at the root of the layout.
        val itemView = inflater.inflate(R.layout.rss_card_view, parent, false)
        //Log.i(LOG_TAG, "Showing Country Info")
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        //return rssChannel.item.size
        return rssItemsList.size
    }

    override fun onBindViewHolder(holder: RssRecyclerAdapter.ViewHolder, position: Int) {

        with(holder) {
            rssTxtItemTitle?.let {
                it.text = rssItemsList[position].title.toString()
                //it.text = "Title"
                it.contentDescription = "Item Title"
            }
            rssTxtItemPubDate?.let {
                it.text = rssItemsList[position].pubDate.toString()
                //it.text = "2020-04-26 2:00:00"
                it.contentDescription = "Item Publish Date"
            }
            rssTxtItemContent?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    it.text = Html.fromHtml(
                        rssItemsList[position].description.toString(),
                        Html.FROM_HTML_MODE_COMPACT
                    )

                } else {
                    it.text = Html.fromHtml(rssItemsList[position].description.toString())
                }
                //it.text = "Description"
                it.contentDescription = "Item Description"

            }
        }
    }

    interface GridItemListener {
        fun onGridItemClick(rssChannel: Channel)
    }
}