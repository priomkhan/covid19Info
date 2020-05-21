package com.priomkhan.covid_19info.ui.detailed

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.priomkhan.covid_19info.LOG_TAG
import com.priomkhan.covid_19info.R
import com.priomkhan.covid_19info.databinding.FragmentDetailedBinding
import com.priomkhan.covid_19info.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.fragment_detailed.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class DetailedFragment : Fragment() {
    private lateinit var sharedViewModel: HomeViewModel
    private lateinit var navController: NavController
    private lateinit var caseGraph: GraphView
    private lateinit var deathGraph: GraphView
    private lateinit var casesDataArrayList: ArrayList<DataPoint>
    private lateinit var deathsDataArrayList: ArrayList<DataPoint>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detailed, container, false)
        sharedViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)




        (requireActivity() as AppCompatActivity).run {

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

        }

        /*
        In a fragment we have to add this line of code more to make the back button work.
         */
        setHasOptionsMenu(true)

        val binding = FragmentDetailedBinding.inflate(
            inflater, container, false
        )
        binding.lifecycleOwner = this


        binding.viewModel = sharedViewModel

        return binding.root
        //return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        Here I am initializing nevController and then we need to add above code to make it work.
        */
        navController = Navigation.findNavController(
            requireActivity(), R.id.nav_host_fragment
        )

        caseGraph = view.findViewById(R.id.caseGraph)
        deathGraph = view.findViewById(R.id.deathGraph)

        val txtProvinceData : TextView = view.findViewById(R.id.txtProvinceinfo)

        sharedViewModel.selectedCountry.observe(requireActivity(), Observer {
            Log.i(LOG_TAG, "Detailed Fragment: Selected Country: ${it.country}")
        })



        sharedViewModel.covid19CountryHistoricalData.observe(requireActivity(), Observer {
            try {

                val casesData = it.timeline.cases
                Log.i(LOG_TAG, "casesData size::  ${casesData.size}")

                casesDataArrayList = ArrayList<DataPoint>()
//               // Log.i(LOG_TAG, "DataPoint ::  ${casesData.keys}")

                val formatter = SimpleDateFormat("MM/dd/yy")

                for (data in casesData) (
                        if (data.key != null && data.value != null) {
                            val date = formatter.parse(data.key)
                            val value: Double = data.value.toDouble()
                            Log.i(LOG_TAG, " DataPoint ::  $date : $value")
                            // array.plus(DataPoint(date, value))
                            casesDataArrayList.add(DataPoint(date, value))
                        }

                        )

                //val series = LineGraphSeries(array)
                //graph.addSeries(series)

                if (casesDataArrayList.size > 29) {
                    Log.i(LOG_TAG, "DataPoint grater then 29")
                    val min = casesDataArrayList[0].x
                    val max = casesDataArrayList[casesDataArrayList.size - 1]
                    Log.i(LOG_TAG, " DataPoint ::  $min : $max")
                    val calendar = Calendar.getInstance()
                    val d1 = calendar.time
                    calendar.add(Calendar.DATE, -casesDataArrayList.size)
                    val d2 = calendar.time


                    val array = arrayOfNulls<DataPoint>(casesDataArrayList.size)
                    val casesDataArray = casesDataArrayList.toArray(array)
                    Log.i(LOG_TAG, "casesDataArray = ${casesDataArray.size}")
                    val series = LineGraphSeries(casesDataArray)
                    caseGraph.addSeries(series)

                    // styling series
                    series.title = "Cases";
                    series.color = Color.YELLOW;
                    series.isDrawDataPoints = true;
                    series.dataPointsRadius;
                    series.thickness = 6;

                    // set date label formatter
                    caseGraph.gridLabelRenderer.labelFormatter =
                        DateAsXAxisLabelFormatter(requireActivity());
                    caseGraph.gridLabelRenderer.numHorizontalLabels =
                        3; // only 4 because of the space
                    caseGraph.title = "Cases"
                    caseGraph.titleColor = Color.BLACK

//
                    // set manual x bounds to have nice steps
                    caseGraph.viewport.setMinX(d2.time.toDouble());
                    caseGraph.viewport.setMaxX(d1.time.toDouble());
                    caseGraph.viewport.isXAxisBoundsManual = true;
                    // caseGraph.gridLabelRenderer.isHumanRounding = false;

                } else {
                    Log.i(LOG_TAG, "DataPoint Less then or equal to 29")
                }

            } catch (e: Exception) {
                Log.i(LOG_TAG, "Exception ::  $e")
            }



            try {

                val deathsData = it.timeline.deaths
                Log.i(LOG_TAG, "deathsData size::  ${deathsData.size}")

                deathsDataArrayList = ArrayList<DataPoint>()
//               // Log.i(LOG_TAG, "DataPoint ::  ${casesData.keys}")

                val formatter = SimpleDateFormat("MM/dd/yy")

                for (data in deathsData) (
                        if (data.key != null && data.value != null) {
                            val date = formatter.parse(data.key)
                            val value: Double = data.value.toDouble()
                            Log.i(LOG_TAG, " DataPoint ::  $date : $value")
                            // array.plus(DataPoint(date, value))
                            deathsDataArrayList.add(DataPoint(date, value))
                        }

                        )

                //val series = LineGraphSeries(array)
                //graph.addSeries(series)

                if (deathsDataArrayList.size > 29) {
                    Log.i(LOG_TAG, "DataPoint grater then 29")
                    val min = deathsDataArrayList[0].x
                    val max = deathsDataArrayList[deathsDataArrayList.size - 1]
                    Log.i(LOG_TAG, " DataPoint ::  $min : $max")
                    val calendar = Calendar.getInstance()
                    val d1 = calendar.time
                    calendar.add(Calendar.DATE, -casesDataArrayList.size)
                    val d2 = calendar.time


                    val array = arrayOfNulls<DataPoint>(deathsDataArrayList.size)
                    val deathsDataArray = deathsDataArrayList.toArray(array)
                    Log.i(LOG_TAG, "deathsDataArray = ${deathsDataArray.size}")
                    val series = LineGraphSeries(deathsDataArray)
                    deathGraph.addSeries(series)

                    // styling series
                    series.title = "Deaths";
                    series.color = Color.RED;
                    series.isDrawDataPoints = true;
                    series.dataPointsRadius;
                    series.thickness = 6;

                    // set date label formatter
                    deathGraph.gridLabelRenderer.labelFormatter =
                        DateAsXAxisLabelFormatter(requireActivity());
                    deathGraph.gridLabelRenderer.numHorizontalLabels =
                        3; // only 4 because of the space
                    deathGraph.title = "Deaths"
                    deathGraph.titleColor = Color.BLACK

                    // set manual x bounds to have nice steps
                    deathGraph.viewport.setMinX(d2.time.toDouble());
                    deathGraph.viewport.setMaxX(d1.time.toDouble());
                    deathGraph.viewport.isXAxisBoundsManual = true;
                    // deathGraph.gridLabelRenderer.isHumanRounding = false;

                } else {
                    Log.i(LOG_TAG, "DataPoint Less then or equal to 29")
                }

            } catch (e: Exception) {
                Log.i(LOG_TAG, "Exception ::  $e")
            }
        }


        )


        sharedViewModel.CountryProvinceData.observe(requireActivity(), Observer {
            var dataText :String = ""
            for(province in it){
                dataText = dataText.plus("Province : ${province.province} \n" +
                        "Cases : ${province.cases} \n" +
                        "Deaths: ${province.deaths}\n\n")
            }

            txtProvinceData.text=dataText
        })


    }


    //Go back to main menu on back button click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            navController.navigateUp()
        }

        return super.onOptionsItemSelected(item)
    }


}
