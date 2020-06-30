package com.gerosprime.gylog.ui.programs.add.exercises

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.exercises.templates.ExerciseTemplateEntity
import com.gerosprime.gylog.ui.programs.R
import com.gerosprime.gylog.ui.programs.databinding.ViewholderWorkoutExerciseExecsBinding

internal class ExerciseExecutionViewHolder (itemView: View,
    var exerciseClickListener: OnItemClickListener<ExerciseExecutionClicked>?)
    : RecyclerView.ViewHolder(itemView) {


    private val binding = ViewholderWorkoutExerciseExecsBinding.bind(itemView)

    private lateinit var exercise : ExerciseTemplateEntity
    private var exerciseIndex: Int = -1
    private var workoutIndex: Int = -1

    private var context = itemView.context


    init {
        itemView.setOnClickListener {
            exerciseClickListener!!.onItemClicked(
                ExerciseExecutionClicked(workoutIndex, exercise, exerciseIndex)
            )
        }
    }

    fun set(
        exercise: ExerciseTemplateEntity,
        workoutIndex: Int,
        exerciseIndex: Int
    ) {
        this.exercise = exercise
        this.workoutIndex = workoutIndex
        this.exerciseIndex = exerciseIndex

        binding.apply {
            viewholderWorkoutExerciseExecsName.text = exercise.name

            val setsFormat : String = context.resources.getQuantityText(R.plurals.sets_format,
                exercise.setTemplates.size).toString()

            viewholderWorkoutExerciseExecsSets.text =
                String.format(setsFormat, exercise.setTemplates.size)

        }


    }

}
