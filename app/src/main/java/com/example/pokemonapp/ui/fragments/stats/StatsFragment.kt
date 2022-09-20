package com.example.pokemonapp.ui.fragments.stats

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokemonapp.util.Constants
import com.example.pokemonapp.databinding.FragmentStatsBinding
import com.example.pokemonapp.models.Pokemon
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import android.icu.number.Precision.currency

import com.github.mikephil.charting.utils.ViewPortHandler
import java.text.DecimalFormat


class StatsFragment : Fragment() {

    lateinit var barList: ArrayList<BarEntry>
    lateinit var barDataSet: BarDataSet
    lateinit var barData: BarData
    lateinit var barChart: HorizontalBarChart

    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment

        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        val args = arguments
        val myBundle: Pokemon = args!!.getParcelable<Pokemon>(Constants.POKEMON_RESULT_KEY) as Pokemon
        //Log.d("POKEMON_BUNDLE", myBundle.toString())
        //Populating
        barList = ArrayList()
        if (myBundle != null) { barList.add(BarEntry(1f, myBundle.stats[0].base_stat.toFloat()))
        barList.add(BarEntry(2f, myBundle.stats[1].base_stat.toFloat()))
        barList.add(BarEntry(3f, myBundle.stats[2].base_stat.toFloat()))
        barList.add(BarEntry(4f, myBundle.stats[3].base_stat.toFloat()))
        barList.add(BarEntry(5f, myBundle.stats[4].base_stat.toFloat()))
        barList.add(BarEntry(6f, myBundle.stats[5].base_stat.toFloat()))
        }
        else {
            Log.d("POKEMON_ERROR", myBundle.toString())
        }

        barDataSet = BarDataSet(barList, "Stats")
        barData= BarData(barDataSet)


        barDataSet.setColors(ColorTemplate.PASTEL_COLORS, 250)
        barDataSet.valueTextColor = Color.GRAY
        barDataSet.valueTextSize=15f
        barDataSet.valueFormatter = FloatFormatter()

        barChart = binding.stats

        barChart.data = barData
        barChart.setPinchZoom(false);
        barChart.axisLeft.setDrawGridLines(false)
        barChart.axisRight.setDrawGridLines(false)
        barChart.xAxis.setDrawGridLines(false)
        val description = Description()
        description.text = ""
        barChart.description = description;    // Hide the description
        barChart.axisLeft.setDrawLabels(false)
        barChart.axisRight.setDrawLabels(false)
        barChart.xAxis.setDrawLabels(true)
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM;
        barChart.xAxis.setDrawAxisLine(false)

        barChart.axisLeft.setDrawAxisLine(false)
        barChart.axisLeft.axisMinimum = 0f;

        barChart.axisRight.setDrawAxisLine(false)
        barChart.axisRight.axisMinimum = 0f;

        barChart.legend.isEnabled = false
        barChart.setDrawBorders(false)
        barChart.animateY(1200, Easing.Linear)
        barChart.xAxis.valueFormatter = MyFormatter()
        barChart.xAxis.granularity = 1f;
        barChart.xAxis.isGranularityEnabled = true;
        barChart.xAxis.textSize = 15f
        barChart.xAxis.textColor = Color.GRAY
        barData.barWidth = 0.1f
        barChart.xAxis.xOffset = 20f
        barChart.invalidate()



        return binding.root
    }
    private class FloatFormatter : ValueFormatter() {
        // override this for BarChart
        override fun getBarLabel(barEntry: BarEntry?): String {
            return barEntry?.y?.toInt().toString()
        }
    }


    private class MyFormatter : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase): String {
            return if (Math.round(value) == 1) {
                "HP" //make it a string and return
            } else if (Math.round(value) == 2) {
                "Attack" //make it a string and return
            } else if (Math.round(value) == 3) {
                "Defense" //make it a string and return
            } else if (Math.round(value) == 4) {
                "Special Attack" //make it a string and return
            } else if (Math.round(value) == 5) {
                "Special Defense"
            } else if (Math.round(value) == 6) {
                "Speed" // return empty for other values where you don't want to print anything on the X Axis
            // }
            } else {
                ""
            }
        }
    }
}