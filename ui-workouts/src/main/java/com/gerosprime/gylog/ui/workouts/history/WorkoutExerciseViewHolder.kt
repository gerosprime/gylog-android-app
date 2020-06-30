package com.gerosprime.gylog.ui.workouts.history

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.models.exercises.performed.ExercisePerformedEntity
import com.gerosprime.gylog.ui.workouts.databinding.ViewholderWorkoutExerciseHistoryBinding
import java.text.DateFormat


class WorkoutExerciseViewHolder(private val performedDateFormat : DateFormat,
                                itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var entity : ExercisePerformedEntity


    private val binding = ViewholderWorkoutExerciseHistoryBinding.bind(itemView)


    fun set(entity : ExercisePerformedEntity) {
        this.entity = entity

        binding.apply {

            viewholderWorkoutExerciseHistoryTitle.text =
                performedDateFormat.format(entity.performedDate)
            viewholderWorkoutExerciseHistorySets.adapter =
                WorkoutExerciseHistorySetAdapter(entity.performedSets)

        }

    }

}