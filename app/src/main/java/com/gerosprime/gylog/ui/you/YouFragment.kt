package com.gerosprime.gylog.ui.you

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.gerosprime.gylog.R
import com.gerosprime.gylog.databinding.FragmentYouBinding
import com.gerosprime.gylog.models.body.fat.LatestBodyFatLoadResult
import com.gerosprime.gylog.models.body.weight.LatestBodyWeightLoadResult
import com.gerosprime.ui.fat.BodyFatGraphViewModel
import com.gerosprime.ui.fat.DefaultBodyFatGraphViewModel
import com.gerosprime.ui.weight.BodyWeightGraphViewModel
import com.gerosprime.ui.weight.DefaultBodyWeightGraphViewModel
import com.google.android.material.tabs.TabLayout
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class YouFragment : Fragment() {

    @Inject
    lateinit var factory : ViewModelProvider.Factory
    private lateinit var viewModelWeight: BodyWeightGraphViewModel
    private lateinit var viewModelFat : BodyFatGraphViewModel

    private lateinit var binding : FragmentYouBinding

    private lateinit var bodyMeasurementHost : NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentYouBinding.inflate(inflater, container, false)

        bodyMeasurementHost = childFragmentManager
            .findFragmentById(R.id.nav_host_fragment_body_measurement) as NavHostFragment
        val navController = bodyMeasurementHost.navController

        binding.fragmentYouBodyMeasurementTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {

                when (tab!!.position) {
                    0 -> navController.navigate(R.id.fragment_body_weight_log)
                    1 -> navController.navigate(R.id.fragment_body_fat_log)
                }

            }
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelWeight = ViewModelProvider(this, factory)
            .get(DefaultBodyWeightGraphViewModel::class.java)
        viewModelFat = ViewModelProvider(this, factory)
            .get(DefaultBodyFatGraphViewModel::class.java)
        viewModelWeight.bodyWeightLatestLiveData.observe(viewLifecycleOwner,
            Observer { latestBodyWeightLoaded(it) })
        viewModelWeight.savedBodyWeightDataLiveData.observe(viewLifecycleOwner, Observer {
            viewModelWeight.loadLatest()
        })
        viewModelFat.bodyWeightLatestLiveData.observe(viewLifecycleOwner,
            Observer { latestBodyFatLoaded(it) })
        viewModelFat.savedBodyFatDataLD.observe(viewLifecycleOwner, Observer {
            viewModelFat.loadLatest()
        })

        viewModelWeight.loadLatest()
        viewModelFat.loadLatest()

    }

    private fun latestBodyWeightLoaded(result : LatestBodyWeightLoadResult) {
        val weight = result.entity
        binding.fragmentYouLatestWeight.text = getString(R.string.weight_format, weight.weight)
    }

    private fun latestBodyFatLoaded(result : LatestBodyFatLoadResult) {
        val fat = result.entity
        binding.fragmentYouLatestFat.text = getString(R.string.fat_format, fat.fat)
    }


}
