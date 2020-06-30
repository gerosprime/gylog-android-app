package com.gerosprime.gylog.ui.programs.detail.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.workouts.WorkoutEntity
import com.gerosprime.gylog.ui.programs.R
import com.gerosprime.gylog.ui.programs.databinding.ViewholderProgramDetailWorkoutBinding


class ProgramDetailViewHolder(itemView : View,
                          var itemClickListener: OnItemClickListener<Long>)
    : RecyclerView.ViewHolder(itemView) {

    private lateinit var workoutEntity: WorkoutEntity

    private val resources = itemView.resources

    private val binding = ViewholderProgramDetailWorkoutBinding.bind(itemView)

    init {
        itemView.setOnClickListener {
            itemClickListener.onItemClicked(workoutEntity.recordId!!)
        }
    }

    fun set(workoutEntity: WorkoutEntity, position: Int) {
        this.workoutEntity = workoutEntity

        val exercises = resources.getQuantityString(R.plurals.exercises_format,
            workoutEntity.exercises.size, workoutEntity.exercises.size)

        binding.apply {
            viewholderProgramDetailWorkoutDay.text = resources.getString(R.string.day_format, position + 1)
            viewholderProgramDetailWorkoutName.text = workoutEntity.name
            viewholderProgramDetailWorkoutExercises.text = exercises
        }

    }

}