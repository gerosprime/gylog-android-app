package com.gerosprime.ui.weight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.gerosprime.gylog.models.body.weight.AllBodyWeightsCacheLoadResult
import com.gerosprime.gylog.models.body.weight.BodyWeightEntity
import com.gerosprime.ui.weight.BodyWeightGraphFragment.DialogTags.BODY_WEIGHT_LOG
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import dagger.android.support.AndroidSupportInjection
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class BodyWeightGraphFragment : Fragment(),
    OnChartValueSelectedListener, BodyWeightLogDialogFragment.Listener {

    object DialogTags {
        const val BODY_WEIGHT_LOG = "body_weight_log_tag"
    }

    private lateinit var currentTextView : TextView
    private lateinit var lineChart : LineChart
    private lateinit var buttonLog : Button

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

        lineChart = inflated.findViewById(R.id.fragment_body_weight_current_chart)
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.xAxis.spaceMin = 0.3f
        lineChart.xAxis.spaceMax = 0.3f
        lineChart.xAxis.setDrawGridLinesBehindData(false)
        lineChart.xAxis.setDrawGridLines(false)
        lineChart.xAxis.textSize = 12f
        lineChart.axisLeft.textSize = 12f
        // lineChart.axisLeft.valueFormatter = WeightValueFormatter()

        lineChart.axisRight.setDrawLabels(false)
        lineChart.legend.isEnabled = false
        lineChart.setOnChartValueSelectedListener(this)

        buttonLog = inflated.findViewById(R.id.fragment_body_weight_graph_log)
        buttonLog.setOnClickListener {
            showBodyWeightLog(null)
        }

        currentTextView = inflated.findViewById(R.id.fragment_body_weight_current_weight)

        return inflated
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.bodyWeightDataMLD.observe(this, Observer { plotBodyWeightsToChart(it) })

        viewModel.savedBodyWeightDataMLD.observe(this, Observer {
            viewModel.loadData()
        })

        if (savedInstanceState == null)
            viewModel.loadData()

        val bodyWeightLogDialog = fragmentManager!!
            .findFragmentByTag(BODY_WEIGHT_LOG) as BodyWeightLogDialogFragment?
        bodyWeightLogDialog?.listener = this

    }

    private fun plotBodyWeightsToChart(result : AllBodyWeightsCacheLoadResult) {

        val size = result.bodyWeightArray.size
        if (result.bodyWeightArray.isNotEmpty())
            currentTextView.text = "${result.bodyWeightArray[size - 1].weight} KG"
        else
            currentTextView.text = ""

        lineChart.xAxis.valueFormatter = DateXValueFormatter(SimpleDateFormat("MMM-dd",
            Locale.US)
                , result.bodyWeightArray)
        lineChart.xAxis.granularity = 1f

        val points : ArrayList<Entry> = arrayListOf()
        val lineDataSet = LineDataSet(points, "")
        lineDataSet.valueTextSize = 12f
        lineDataSet.valueFormatter = WeightValueFormatter()
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        lineDataSet.lineWidth = 2.5f

        for (bodyWeightEntity in result.bodyWeightArray) {
            lineDataSet.addEntry(
                Entry(bodyWeightEntity.recordId!!.toFloat(), bodyWeightEntity.weight,
                    bodyWeightEntity))
        }
        lineChart.data = LineData(lineDataSet)
        lineChart.invalidate()

    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {

        val bodyWeight = e!!.data as BodyWeightEntity
        showBodyWeightLog(bodyWeight)
    }

    private fun showBodyWeightLog(bodyWeight: BodyWeightEntity?) {
        var dialog : BodyWeightLogDialogFragment = if (bodyWeight != null) {

            BodyWeightLogDialogFragment.createInstance(
                bodyWeight.recordId, bodyWeight.weight,
                bodyWeight.dateLogged, bodyWeight.note
            )

        } else {
            BodyWeightLogDialogFragment.createInstance(null, null,
                Date(), "")
        }

        dialog.listener = this
            dialog.show(fragmentManager!!, BODY_WEIGHT_LOG)

    }

    override fun onNothingSelected() {
    }

    override fun bodyWeightSet(recordId: Long?, weight: Float, date: Date, notes: String) {
        viewModel.saveBodyWeightLog(recordId, weight, date, notes)
    }
}