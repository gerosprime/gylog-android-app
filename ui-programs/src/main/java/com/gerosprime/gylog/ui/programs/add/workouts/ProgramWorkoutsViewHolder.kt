package com.gerosprime.gylog.ui.programs.add.workouts

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.workouts.WorkoutEntity
import com.gerosprime.gylog.ui.programs.R
import com.gerosprime.gylog.ui.programs.add.exercises.ExerciseExecutionAdapter
import com.gerosprime.gylog.ui.programs.add.exercises.ExerciseExecutionClicked
import com.gerosprime.gylog.ui.programs.databinding.ViewholderProgramWorkoutsBinding

class ProgramWorkoutsViewHolder(itemView : View,
                                         private var exerciseWorkoutListener
                                            : OnItemClickListener<Int>? = null,
                                         private var exerciseClickListener
                                            : OnItemClickListener<ExerciseExecutionClicked>? = null)

    : RecyclerView.ViewHolder(itemView) {

    private var workoutEntity : WorkoutEntity? = null
    private var workoutIndex : Int = -1

    private val binding = ViewholderProgramWorkoutsBinding.bind(itemView)

    private var context = itemView.context

    init {

        binding.apply {
            viewholderProgramWorkoutsAddExercise.setOnClickListener {
                exerciseWorkoutListener!!.onItemClicked(workoutIndex)
            }

            viewholderProgramWorkoutsExercises.adapter = ExerciseExecutionAdapter(
                listOf(),
                -1,
                exerciseClickListener)
            viewholderProgramWorkoutsExercises.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }


    }

    fun set(workoutEntity : WorkoutEntity, workoutIndex : Int) {
        this.workoutEntity = workoutEntity
        this.workoutIndex = workoutIndex

        binding.apply {

            viewholderProgramWorkoutsTitle.text = workoutEntity.name
            viewholderProgramWorkoutsSubtitle.text =
                context.getString(R.string.day_format, workoutEntity.day)

            val exercises = workoutEntity.exercises
            if (exercises.isNullOrEmpty()) {
                viewholderProgramWorkoutsEmpty.visibility = View.VISIBLE
                viewholderProgramWorkoutsExercises.visibility = View.INVISIBLE
            } else {

                viewholderProgramWorkoutsEmpty.visibility = View.INVISIBLE
                viewholderProgramWorkoutsExercises.visibility = View.VISIBLE

            }

            val adapter = viewholderProgramWorkoutsExercises.adapter
                    as ExerciseExecutionAdapter
            adapter.exercises = exercises
            adapter.workoutIndex = workoutIndex
            adapter.notifyItemRangeChanged(0, exercises.size)

        }

    }

}