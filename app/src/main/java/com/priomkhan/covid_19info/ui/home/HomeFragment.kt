package com.priomkhan.covid_19info.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.priomkhan.covid_19info.LOG_TAG
import com.priomkhan.covid_19info.R
import com.priomkhan.covid_19info.covid19DataV2.CountriesV2
import com.rilixtech.widget.countrycodepicker.CountryCodePicker


class HomeFragment : Fragment(), HomeRecyclerAdapter.GridItemListener {

    private lateinit var homeViewModel: HomeViewModel
    lateinit var ccp: CountryCodePicker
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvTotalCaseCount: TextView
    private lateinit var tvTotalDeathCount: TextView
    private lateinit var tvTotalRecoveredCount: TextView
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        recyclerView = view.findViewById(R.id.recyclerView) as RecyclerView
        ccp = view.findViewById(R.id.ccp)
        tvTotalCaseCount = view.findViewById(R.id.tvTotalCaseCount)
        tvTotalDeathCount = view.findViewById(R.id.tvTotalDeathCount)
        tvTotalRecoveredCount = view.findViewById(R.id.tvTotalRecoveredCount)

        navController = Navigation.findNavController(
            requireActivity(), R.id.nav_host_fragment
        )


        homeViewModel.covid19GlobalData.observe(requireActivity(), Observer {
            //Log.i(LOG_TAG, "Data Fatching Complete: Displaying Data.... ")
            // val globalCovid19Info: String = (" TotalCase = ${it.cases} : TotalDeaths = ${it.deaths} : TotalRecovered = ${it.recovered} ")
            // Log.i(LOG_TAG, "Global ::  $globalCovid19Info")

            try {
                tvTotalCaseCount.text = it.cases.toString()
                tvTotalDeathCount.text = it.deaths.toString()
                tvTotalRecoveredCount.text = it.recovered.toString()
            } catch (e: Exception) {
                Log.i(LOG_TAG, "Exception ::  $e")
            }

        })


        ccp.setOnCountryChangeListener { selectedCountry ->
            val countryName: String = selectedCountry.name
//            Toast.makeText(
//                context,
//                "Updated $countryName",
//                Toast.LENGTH_SHORT
//            ).show()

            homeViewModel.getCountryInfo(countryName)
        }



        homeViewModel.covid19CountryData.observe(requireActivity(), Observer {
            //Log.i(LOG_TAG, "Data Fatching Complete: Displaying Data.... ")
            val countryData: String =
                (" ${it.country} : TotalCase = ${it.cases} : TotalDeaths = ${it.deaths} : TotalRecovered = ${it.recovered} ")
            Log.i(LOG_TAG, "Country ::  $countryData")

            try {
                val adapter = HomeRecyclerAdapter(requireContext(), it, this)
//                val adapter = HomeRecyclerAdapter(requireContext(), CountriesV2("Default",
//                    CountryInfo(0.0,0.0,"DefaultFlagURL"),100,100,100,100,100,100,100,10.0,10.0,100,10.0
//                ),this)
                recyclerView.adapter = adapter
                recyclerView.adapter?.notifyDataSetChanged()
            } catch (e: Exception) {
                Log.i(LOG_TAG, "Exception ::  $e")
            }

        })

        homeViewModel.busyBool.observe(requireActivity(), Observer {

            if (it) {
                Log.i(LOG_TAG, "Busy Bool = True")
            } else {
                Log.i(LOG_TAG, "Busy Bool = False")
                //recyclerView.adapter?.notifyDataSetChanged()
            }

        })


        return view
    }


    override fun onGridItemClick(countryInfo: CountriesV2) {
//        Toast.makeText(
//            context,
//            "Selected: ${countryInfo.country}",
//            Toast.LENGTH_SHORT
//        ).show()
        homeViewModel.getCountryHistoricalInfo(countryInfo.country)
        homeViewModel.getCountryProvinceInfo(countryInfo.country)

        homeViewModel.selectedCountry.postValue(countryInfo)

        navController.navigate(R.id.nav_to_detailedFragment)
    }


/*
//Old API Code:
    //        homeViewModel.covid19Data.observe(requireActivity(), Observer {
//            Log.i(LOG_TAG, "Data Fatching Complete: Displaying Data.... ")
//            val  globalCovid19Info : String = ("NewConfirmed = ${it.Global.NewConfirmed} : TotalConfirmed = ${it.Global.TotalConfirmed} : NewDeaths = ${it.Global.NewDeaths} : TotalDeaths = ${it.Global.TotalDeaths} : TotalRecovered = ${it.Global.TotalRecovered} ")
//            Log.i(LOG_TAG, "Global ::  $globalCovid19Info")
//
//            Log.i(LOG_TAG, "Countries :: ")
//
//            for((count, countryData) in it.Countries.withIndex()){
//                //countryNames.append("$data.country : ( ${data.countryInfo.lat} : ${data.countryInfo.long}) : ${data.deaths}").append("\n")
//                val countryCovid19Info: String = (" ${countryData.country} : NewConfirmed = ${countryData.newConfirmed} : TotalConfirmed = ${countryData.totalConfirmed}: NewDeaths = ${countryData.newDeaths} : totalDeaths = ${countryData.totalDeaths} : totalRecovered = ${countryData.totalRecovered} : UpdatedOn = ${countryData.date}")
//                Log.i(LOG_TAG, "#$count ::  $countryCovid19Info")
//            }
//
//            //textView.text = countryNames
//
//        })


//
//        homeViewModel.covid19CountryData.observe(requireActivity(), Observer {
//
//
//           // var countryCovid19Info: String = ("${it.Country} : (${it.NewConfirmed} : ${it.TotalConfirmed}): ${it.NewDeaths} : ${it.TotalDeaths} : ${it.TotalRecovered} ")
//
//
//            Log.i(LOG_TAG, "Data Fatching Complete: Displaying Data....")
//           // Log.i(LOG_TAG, "$countryCovid19Info")
//            //textView.text = countryNames
//        })
 */


}
