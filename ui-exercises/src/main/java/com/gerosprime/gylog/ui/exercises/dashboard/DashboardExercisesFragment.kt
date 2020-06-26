package com.gerosprime.gylog.ui.exercises.dashboard

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.base.utils.FetchStateUtils
import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.ui.exercises.R
import com.gerosprime.gylog.ui.exercises.databinding.FragmentDashboardExercisesBinding
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class DashboardExercisesFragment :Fragment() {

    object RequestCodes {
        const val EXERCISE_DETAIL = 1
    }

    @Inject
    lateinit var factory : ViewModelProvider.Factory
    lateinit var viewModel: DashboardExercisesViewModel

    interface Listener {
        fun onExerciseItemClick(exerciseItem : ExerciseItemClick)
    }

    private lateinit var binding : FragmentDashboardExercisesBinding

    lateinit var listener : Listener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Listener) {
            listener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory)
            .get(DefaultDashboardExercisesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflated = inflater.inflate(R.layout.fragment_dashboard_exercises,
            container, false)
        binding = FragmentDashboardExercisesBinding.bind(inflated)
        return inflated
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.fetchStateLiveData.observe(viewLifecycleOwner, Observer { fetchStateChanged(it) })
        viewModel.exercisesLiveData.observe(viewLifecycleOwner, Observer { populateExercises(exercises = it) })

        if (savedInstanceState == null) {

            if (TedPermission.isGranted(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                viewModel.loadExercises()
            } else {
                TedPermission.with(context)
                    .setRationaleMessage(R.string.read_permission_rationale)
                    .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .setPermissionListener(object : PermissionListener {
                        override fun onPermissionGranted() {
                            viewModel.loadExercises()
                        }

                        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                            viewModel.loadExercises()
                        }
                    }).check()

            }

        }
    }

    private fun populateExercises(exercises : List<ExerciseEntity>) {
        val adapter = DashboardExercisesAdapter(exercises)
        binding.dashboardExercises.adapter = adapter
        adapter.listener = object : OnItemClickListener<ExerciseItemClick> {
            override fun onItemClicked(item: ExerciseItemClick) {
                listener.onExerciseItemClick(item)
            }
        }
    }

    private fun fetchStateChanged(fetchState: FetchState) {
        FetchStateUtils.updateViewContentsByState(fetchState,
            binding.dashboardExercises,
            binding.dashboardProgress, binding.errorContent)
    }

    fun notifyItemUpdated(index: Int) {
        binding.dashboardExercises.adapter?.notifyItemChanged(index)
    }

    fun notifyItemInserted(index: Int) {
        binding.dashboardExercises.adapter?.notifyItemInserted(index)
        binding.dashboardExercises.scrollToPosition(index)
    }

}