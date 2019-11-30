package com.gerosprime.ui.weight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.gerosprime.gylog.models.body.weight.AllBodyWeightsCacheLoadResult
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class BodyWeightGraphFragment : Fragment() {

    private lateinit var currentTextView : TextView
    private lateinit var chart : LineChart

    @Inject
    lateinit var factory : ViewModelProvider.Factory
    private lateinit var viewModel : BodyWeightGraphViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory)
            .get(DefaultBodyWeightGraphViewModel::class.java)
    }

    fun reloadGraph() {
        viewModel.loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val inflated = inflater.inflate(R.layout.fragment_body_weight_graph, container, false)

        chart = inflated.findViewById(R.id.fragment_body_weight_current_chart)
        currentTextView = inflated.findViewById(R.id.fragment_body_weight_current_weight)

        return inflated
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.bodyWeightDataMLD.observe(this, Observer { plotBodyWeightsToChart(it) })

        if (savedInstanceState == null)

            viewModel.loadData()
    }

    private fun plotBodyWeightsToChart(result : AllBodyWeightsCacheLoadResult) {

        val dataSet = LineDataSet(Entry())
        val data = LineData()
        data.dataSets

        chart.lineData

    }

}