package com.gerosprime.gylog.ui.workouts.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.models.workouts.history.WorkoutExerciseHistoryResult
import com.gerosprime.gylog.ui.workouts.R
import com.gerosprime.gylog.ui.workouts.history.WorkoutExerciseHistoryFragment.Extras.EXERCISE_ID
import com.gerosprime.gylog.ui.workouts.history.WorkoutExerciseHistoryFragment.Extras.WORKOUT_ID
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class WorkoutExerciseHistoryFragment : BottomSheetDialogFragment() {


    lateinit var viewModel : WorkoutExerciseHistoryViewModel
    @Inject
    lateinit var factory : ViewModelProvider.Factory

    lateinit var toolbar : Toolbar

    lateinit var recyclerViewHistory : RecyclerView

    lateinit var emptyTextView : TextView

    companion object {
        fun createInstance(workoutId : Long, exerciseId : Long)
                : WorkoutExerciseHistoryFragment {

            val argument = Bundle()
            argument.putLong(WORKOUT_ID, workoutId)
            argument.putLong(EXERCISE_ID, exerciseId)

            val fragment = WorkoutExerciseHistoryFragment()
            fragment.arguments = argument

            return fragment
        }
    }

    object Extras {
        const val WORKOUT_ID = "extra_workout_id"
        const val EXERCISE_ID = "extra_exercise_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, factory)
            .get(DefaultWorkoutExerciseHistoryViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val inflated = inflater.inflate(R.layout.fragment_workout_history_dialog, container)
        toolbar = inflated.findViewById(R.id.fragment_workout_history_toolbar)
        toolbar.setOnClickListener {
            dismiss()
        }
        emptyTextView = inflated.findViewById(R.id.fragment_workout_history_exercises_empty)
        recyclerViewHistory = inflated.findViewById(R.id.fragment_workout_history_exercises)
        recyclerViewHistory.addItemDecoration(DividerItemDecoration(context,
            DividerItemDecoration.VERTICAL))

        return inflated
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.workoutExerciseHistoryMLD.observe(viewLifecycleOwner, Observer {
            populateExercises(it)
        })

        if (savedInstanceState == null) {
            viewModel.loadExerciseHistory(getWorkoutId(), getExerciseId())
        }
    }

    private fun populateExercises(result: WorkoutExerciseHistoryResult) {


        toolbar.title = result.exercise.name

        if (result.exercises.isEmpty()) {
            recyclerViewHistory.visibility = View.GONE
            emptyTextView.visibility = View.VISIBLE
        } else {
            emptyTextView.visibility = View.GONE
            recyclerViewHistory.adapter =
                WorkoutExerciseHistoryAdapter(
                    SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()),
                    result.exercises
                )
        }
    }

    private fun getWorkoutId() : Long {
        return arguments!!.getLong(WORKOUT_ID, -1L)
    }

    private fun getExerciseId() : Long {
        return arguments!!.getLong(EXERCISE_ID, -1L)
    }

}