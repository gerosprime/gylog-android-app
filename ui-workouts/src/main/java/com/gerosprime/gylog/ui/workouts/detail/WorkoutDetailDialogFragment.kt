package com.gerosprime.gylog.ui.workouts.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.base.utils.TimeFormatUtil
import com.gerosprime.gylog.models.workouts.detail.LoadWorkoutFromCacheResult
import com.gerosprime.gylog.ui.workouts.R
import com.gerosprime.gylog.ui.workouts.detail.adapter.WorkoutDetailAdapter
import com.gerosprime.gylog.ui.workouts.detail.adapter.WorkoutExerciseClick
import com.gerosprime.gylog.ui.workouts.history.WorkoutExerciseHistoryFragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.activity_workout_session.*
import javax.inject.Inject

class WorkoutDetailDialogFragment : DialogFragment() {

    lateinit var cancelButton : Button
    lateinit var startButton : Button

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var viewModel: WorkoutDetailDialogViewModel

    lateinit var titleTextView: TextView
    lateinit var subTitleTextView: TextView

    lateinit var durationTextView : TextView
    lateinit var exercisesRecyclerView: RecyclerView

    private var listener : OnStartClickListener? = null

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

        titleTextView = inflated.findViewById(R.id.fragment_workout_detail_dialog_title)
        subTitleTextView = inflated.findViewById(R.id.fragment_workout_detail_dialog_subtitle)
        durationTextView = inflated.findViewById(R.id.fragment_workout_detail_dialog_duration)
        exercisesRecyclerView = inflated.findViewById(R.id.fragment_workout_detail_exercises)
        exercisesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        cancelButton = inflated.findViewById(R.id.fragment_workout_detail_dialog_cancel)
        cancelButton.setOnClickListener { dismiss() }
        startButton = inflated.findViewById(R.id.fragment_workout_detail_dialog_start)
        startButton.setOnClickListener {
            listener!!.onStartClicked(getWorkoutRecordID())
            dismiss()
        }

        return inflated

    }

    private fun populateWorkoutDetail(result: LoadWorkoutFromCacheResult) {

        val workout = result.workout
        exercisesRecyclerView.adapter = WorkoutDetailAdapter(exerciseClickListener,
            workout.exercises)
        durationTextView.text = TimeFormatUtil.secondsToString(result.durationSeconds.toLong())
        titleTextView.text = workout.name
        subTitleTextView.text = workout.description

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, factory)
            .get(DefaultWorkoutDetailViewModel::class.java)

        viewModel.workoutLoadCacheResultMLD.observe(this,
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