package com.gerosprime.gylog.ui.you

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController

import com.gerosprime.gylog.R
import com.google.android.material.tabs.TabLayout

class YouFragment : Fragment() {

    companion object {
        fun newInstance() = YouFragment()
    }

    private lateinit var viewModel: YouViewModel

    lateinit var bodyMeasurementTab : TabLayout

    lateinit var bodyMeasurementHost : NavHostFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val inflated = inflater.inflate(R.layout.fragment_you, container, false)

        bodyMeasurementHost = childFragmentManager
            .findFragmentById(R.id.nav_host_fragment_body_measurement) as NavHostFragment
        val navController = bodyMeasurementHost.navController

        bodyMeasurementTab = inflated.findViewById(R.id.fragment_you_body_measurement_tab)
        bodyMeasurementTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {

                when (tab!!.position) {
                    0 -> navController.navigate(R.id.fragment_body_weight_log)
                    1 -> navController.navigate(R.id.fragment_body_fat_log)
                }

            }
        })

        return inflated
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(YouViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
