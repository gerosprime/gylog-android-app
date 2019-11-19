package com.gerosprime.gylog.ui.exercises.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.base.utils.FetchStateUtils
import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.ui.exercises.R
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class DashboardExercisesFragment :Fragment() {


    @Inject
    lateinit var factory : ViewModelProvider.Factory
    lateinit var viewModel: DashboardExercisesViewModel

    lateinit var progressContentView : View
    lateinit var exercisesRecyclerView : RecyclerView
    lateinit var errorContentView : View

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory)
            .get(DefaultDashboardExercisesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var inflated = inflater.inflate(R.layout.fragment_dashboard_exercises,
            container, false)

        exercisesRecyclerView = inflated.findViewById(R.id.fragment_dashboard_exercises)
        progressContentView = inflated.findViewById(R.id.fragment_dashboard_progress)
        errorContentView = inflated.findViewById(R.id.fragment_dashboard_exercises_errorcontent)

        return inflated
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.fetchStateLiveData.observe(this, Observer { fetchStateChanged(it) })
        viewModel.exercisesLiveData.observe(this, Observer { populateExercises(exercises = it) })
        viewModel.loadExercises()
    }

    private fun populateExercises(exercises : List<ExerciseEntity>) {
        exercisesRecyclerView.adapter = DashboardExercisesAdapter(exercises)
    }

    private fun fetchStateChanged(fetchState: FetchState) {
        FetchStateUtils.updateViewContentsByState(fetchState,
            exercisesRecyclerView, progressContentView, errorContentView)
    }

    fun notifyItemUpdated(index: Int) {
        exercisesRecyclerView.adapter?.notifyItemChanged(index)
    }

    fun notifyItemInserted(index: Int) {
        exercisesRecyclerView.adapter?.notifyItemInserted(index)
        exercisesRecyclerView.scrollToPosition(index)
    }

}