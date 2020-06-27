package com.gerosprime.ui.fat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gerosprime.gylog.models.body.fat.AllBodyFatsCacheLoadResult
import com.gerosprime.gylog.models.body.fat.BodyFatEntity
import com.gerosprime.ui.fat.BodyFatGraphFragment.DialogTags.BODY_WEIGHT_LOG
import com.gerosprime.ui.fat.databinding.FragmentBodyFatGraphBinding
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

class BodyFatGraphFragment : Fragment(),
    OnChartValueSelectedListener, BodyFatLogDialogFragment.Listener {

    object DialogTags {
        const val BODY_WEIGHT_LOG = "body_weight_log_tag"
    }


    @Inject
    lateinit var factory : ViewModelProvider.Factory
    private lateinit var viewModel : BodyFatGraphViewModel

    private lateinit var binding : FragmentBodyFatGraphBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory)
            .get(DefaultBodyFatGraphViewModel::class.java)

    }

    fun reloadGraph() {
        viewModel.loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBodyFatGraphBinding.inflate(inflater, container, false)

        binding.fragmentBodyFatCurrentChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.fragmentBodyFatCurrentChart.xAxis.spaceMin = 0.3f
        binding.fragmentBodyFatCurrentChart.xAxis.spaceMax = 0.3f
        binding.fragmentBodyFatCurrentChart.xAxis.setDrawGridLinesBehindData(false)
        binding.fragmentBodyFatCurrentChart.xAxis.setDrawGridLines(false)
        binding.fragmentBodyFatCurrentChart.xAxis.textSize = 12f
        binding.fragmentBodyFatCurrentChart.axisLeft.textSize = 12f

        // lineChart.axisLeft.valueFormatter = WeightValueFormatter()

        binding.fragmentBodyFatCurrentChart.axisRight.setDrawLabels(false)
        binding.fragmentBodyFatCurrentChart.legend.isEnabled = false
        binding.fragmentBodyFatCurrentChart.setOnChartValueSelectedListener(this)


        binding.fragmentBodyFatGraphLog.setOnClickListener {
            showBodyFatLog(null)
        }


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.bodyFatDataLD.observe(viewLifecycleOwner, Observer { plotBodyWeightsToChart(it) })

        viewModel.savedBodyFatDataLD.observe(viewLifecycleOwner, Observer {
            viewModel.loadData()
        })

        if (savedInstanceState == null)
            viewModel.loadData()

        val bodyWeightLogDialog = fragmentManager!!
            .findFragmentByTag(BODY_WEIGHT_LOG) as BodyFatLogDialogFragment?
        bodyWeightLogDialog?.listener = this

    }

    private fun plotBodyWeightsToChart(result : AllBodyFatsCacheLoadResult) {

        val size = result.bodyFatArray.size
        if (result.bodyFatArray.isNotEmpty())
            binding.fragmentBodyFatCurrentWeight.text = "${result.bodyFatArray[size - 1].fat} KG"
        else
            binding.fragmentBodyFatCurrentWeight.text = ""

        binding.fragmentBodyFatCurrentChart.xAxis.valueFormatter = DateXValueFormatter(SimpleDateFormat("MMM-dd",
            Locale.US)
                , result.bodyFatArray)
        binding.fragmentBodyFatCurrentChart.xAxis.granularity = 1f

        val points : ArrayList<Entry> = arrayListOf()
        val lineDataSet = LineDataSet(points, "")
        lineDataSet.valueTextSize = 12f
        lineDataSet.valueFormatter = FatValueFormatter()
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        lineDataSet.lineWidth = 2.5f

        for (bodyFatEntity in result.bodyFatArray) {
            lineDataSet.addEntry(
                Entry(bodyFatEntity.recordId!!.toFloat(), bodyFatEntity.fat,
                    bodyFatEntity))
        }
        binding.fragmentBodyFatCurrentChart.data = LineData(lineDataSet)
        binding.fragmentBodyFatCurrentChart.invalidate()

    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {

        val bodyWeight = e!!.data as BodyFatEntity
        showBodyFatLog(bodyWeight)
    }

    private fun showBodyFatLog(bodyWeight: BodyFatEntity?) {
        var dialog : BodyFatLogDialogFragment = if (bodyWeight != null) {

            BodyFatLogDialogFragment.createInstance(
                bodyWeight.recordId, bodyWeight.fat,
                bodyWeight.dateLogged, bodyWeight.note
            )

        } else {
            BodyFatLogDialogFragment.createInstance(
                null, null,
                Date(), ""
            )
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