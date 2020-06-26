package com.gerosprime.gylog.ui.workouts.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.base.utils.TimeFormatUtil
import com.gerosprime.gylog.models.workouts.detail.LoadWorkoutFromCacheResult
import com.gerosprime.gylog.ui.workouts.R
import com.gerosprime.gylog.ui.workouts.databinding.FragmentWorkoutDetailDialogBinding
import com.gerosprime.gylog.ui.workouts.detail.adapter.WorkoutDetailAdapter
import com.gerosprime.gylog.ui.workouts.detail.adapter.WorkoutExerciseClick
import com.gerosprime.gylog.ui.workouts.history.WorkoutExerciseHistoryFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class WorkoutDetailDialogFragment : DialogFragment() {

    lateinit var cancelButton : Button
    lateinit var startButton : Button

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var viewModel: WorkoutDetailDialogViewModel

    private var listener : OnStartClickListener? = null

    private lateinit var binding : FragmentWorkoutDetailDialogBinding

    private var exerciseClickListener = object : OnItemClickListener<WorkoutExerciseClick> {
        override fun onItemClicked(item: WorkoutExerciseClick) {
            val historyFragment =
                WorkoutExerciseHistoryFragment.createInstance(getWorkoutRecordID(),
                    item.entity.exerciseId)
            historyFragment.show(childFragmentManager, "")
        }
    }

    companion object Factory {
        fun createInstance(workoutRecordId: Long) : WorkoutDetailDialogFragment {
            val instance = WorkoutDetailDialogFragment()
            val arguments = Bundle()
            arguments.putLong(Extras.WORKOUT_RECORD_ID, workoutRecordId)
            instance.arguments = arguments
            return instance
        }
    }

    private object Extras {
        const val WORKOUT_RECORD_ID = "extra_workout_recordId"
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)

        if (context is OnStartClickListener) {
            listener = context
        }
    }

    override fun onDestroy() {
        listener = null
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val inflated =  inflater.inflate(R.layout.fragment_workout_detail_dialog,
            container, false)

        binding = FragmentWorkoutDetailDialogBinding.bind(inflated)

        binding.fragmentWorkoutDetailExercises.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.fragmentWorkoutDetailDialogCancel.setOnClickListener { dismiss() }
        binding.fragmentWorkoutDetailDialogStart.setOnClickListener {
            listener!!.onStartClicked(getWorkoutRecordID())
            dismiss()
        }

        return inflated

    }

    private fun populateWorkoutDetail(result: LoadWorkoutFromCacheResult) {

        val workout = result.workout
        binding.fragmentWorkoutDetailExercises.adapter = WorkoutDetailAdapter(exerciseClickListener,
            workout.exercises)
        binding.fragmentWorkoutDetailDialogDuration.text = TimeFormatUtil.secondsToString(result.durationSeconds.toLong())
        binding.fragmentWorkoutDetailDialogTitle.text = workout.name
        binding.fragmentWorkoutDetailDialogSubtitle.text = workout.description

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, factory)
            .get(DefaultWorkoutDetailViewModel::class.java)

        viewModel.workoutLoadCacheResultLD.observe(viewLifecycleOwner,
            Observer {
            populateWorkoutDetail(it)
        })

        if (savedInstanceState == null)
            viewModel.loadWorkout(getWorkoutRecordID())
    }

    private fun getWorkoutRecordID() : Long {
        return arguments!!.getLong(Extras.WORKOUT_RECORD_ID)
    }

    interface OnStartClickListener {
        fun onStartClicked(workoutRecordId: Long)
    }

}