package com.priomkhan.covid_19info.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.priomkhan.covid_19info.LOG_TAG
import com.priomkhan.covid_19info.R
import com.priomkhan.covid_19info.covid19DataV2.CountriesV2

class HomeRecyclerAdapter(
    val context: Context,
    private val countryCovidInfo: CountriesV2,
    private val itemListener: GridItemListener
) : RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val countryFragImage: ImageView = itemView.findViewById<ImageView>(R.id.giFlagImage)

        val countryName: TextView = itemView.findViewById<TextView>(R.id.giCountryName)

        val tvCaseCount: TextView = itemView.findViewById<TextView>(R.id.giCaseCount)

        val tvDeathCount: TextView = itemView.findViewById<TextView>(R.id.giDeathCount)

        val tvRecoveredCount: TextView = itemView.findViewById<TextView>(R.id.giRecoveredCount)

    }


    /*
    The job of onCreateViewHolder function is to create a layout view.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context) //The parent is the ViewGroup at the root of the layout.
        val itemView = inflater.inflate(R.layout.grid_item, parent, false)
        Log.i(LOG_TAG, "Showing Country Info")
        return ViewHolder(itemView)
    }

    /*
     onBindViewHolder function bind data to the ViewHolder.

     The holder reference is passed in when this function is called and it'll be called for
     each item in the grid.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val countryInfo = countryCovidInfo

        with(holder) {
            countryName?.let {
                it.text = countryInfo.country.toString()
                it.contentDescription = "Total Cases"
            }

            tvCaseCount?.let {
                it.text = countryInfo.cases.toString()
                it.contentDescription = "Total Cases"
            }

            //
            tvDeathCount?.let {
                it.text = countryInfo.deaths.toString()
                it.contentDescription = "Total Deaths"
            }

            tvRecoveredCount?.let {
                it.text = countryInfo.recovered.toString()

                it.contentDescription = "Total Recovered"
            }

            //
            Glide.with(context)
                .load(countryInfo.countryInfo.flag)
                .override(60, 60)
                .fitCenter()
                .into(countryFragImage)

            holder.itemView.setOnClickListener {
                itemListener.onGridItemClick(countryInfo)
            }

        }
    }

    interface GridItemListener {
        fun onGridItemClick(countryInfo: CountriesV2)
    }

    override fun getItemCount(): Int {
        return 1
    }


}