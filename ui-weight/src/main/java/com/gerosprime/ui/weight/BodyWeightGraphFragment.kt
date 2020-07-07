package com.gerosprime.ui.weight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gerosprime.gylog.models.body.weight.AllBodyWeightsCacheLoadResult
import com.gerosprime.gylog.models.body.weight.BodyWeightEntity
import com.gerosprime.ui.weight.BodyWeightGraphFragment.DialogTags.BODY_WEIGHT_LOG
import com.gerosprime.ui.weight.databinding.FragmentBodyWeightGraphBinding
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


    @Inject
    lateinit var factory : ViewModelProvider.Factory
    private lateinit var viewModel : BodyWeightGraphViewModel

    private lateinit var binding : FragmentBodyWeightGraphBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory)
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

        binding = FragmentBodyWeightGraphBinding.inflate(inflater, container, false)

        binding.fragmentBodyWeightCurrentChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.fragmentBodyWeightCurrentChart.xAxis.spaceMin = 0.3f
        binding.fragmentBodyWeightCurrentChart.xAxis.spaceMax = 0.3f
        binding.fragmentBodyWeightCurrentChart.xAxis.setDrawGridLinesBehindData(false)
        binding.fragmentBodyWeightCurrentChart.xAxis.setDrawGridLines(false)
        binding.fragmentBodyWeightCurrentChart.xAxis.textSize = 12f
        binding.fragmentBodyWeightCurrentChart.axisLeft.textSize = 12f
        // lineChart.axisLeft.valueFormatter = WeightValueFormatter()

        binding.fragmentBodyWeightCurrentChart.axisRight.setDrawLabels(false)
        binding.fragmentBodyWeightCurrentChart.legend.isEnabled = false
        binding.fragmentBodyWeightCurrentChart.setOnChartValueSelectedListener(this)

        binding.fragmentBodyWeightGraphLog.setOnClickListener {
            showBodyWeightLog(null)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.run {
            bodyWeightDataLiveData.observe(viewLifecycleOwner, Observer { plotBodyWeightsToChart(it) })

            savedBodyWeightDataLiveData.observe(viewLifecycleOwner, Observer {
                viewModel.loadData()
            })
        }


        if (savedInstanceState == null)
            viewModel.loadData()

        val bodyWeightLogDialog = childFragmentManager
            .findFragmentByTag(BODY_WEIGHT_LOG) as BodyWeightLogDialogFragment?
        bodyWeightLogDialog?.listener = this

    }

    private fun plotBodyWeightsToChart(result : AllBodyWeightsCacheLoadResult) {

        val size = result.bodyWeightArray.size

        binding.run {

            if (result.bodyWeightArray.isNotEmpty())
                fragmentBodyWeightCurrentWeight.text = "${result.bodyWeightArray[size - 1].weight} KG"
            else
                fragmentBodyWeightCurrentWeight.text = ""

            fragmentBodyWeightCurrentChart.xAxis.valueFormatter = DateXValueFormatter(SimpleDateFormat("MMM-dd",
                Locale.US)
                , result.bodyWeightArray)
            fragmentBodyWeightCurrentChart.xAxis.granularity = 1f

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

            fragmentBodyWeightCurrentChart.data = LineData(lineDataSet)
            fragmentBodyWeightCurrentChart.invalidate()

        }


    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {

        val bodyWeight = e!!.data as BodyWeightEntity
        showBodyWeightLog(bodyWeight)
    }

    private fun showBodyWeightLog(bodyWeight: BodyWeightEntity?) {
        val dialog : BodyWeightLogDialogFragment = if (bodyWeight != null) {

            BodyWeightLogDialogFragment.createInstance(
                bodyWeight.recordId, bodyWeight.weight,
                bodyWeight.dateLogged, bodyWeight.note
            )

        } else {
            BodyWeightLogDialogFragment.createInstance(null, null,
                Date(), "")
        }

        dialog.listener = this
            dialog.show(parentFragmentManager, BODY_WEIGHT_LOG)

    }

    override fun onNothingSelected() {
    }

    override fun bodyWeightSet(recordId: Long?, weight: Float, date: Date, notes: String) {
        viewModel.saveBodyWeightLog(recordId, weight, date, notes)
    }
}