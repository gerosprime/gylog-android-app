package com.gerosprime.gylog.ui.programs.detail.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.workouts.WorkoutEntity
import com.gerosprime.gylog.ui.programs.R


class ProgramDetailViewHolder(itemView : View,
                          var itemClickListener: OnItemClickListener<Long>)
    : RecyclerView.ViewHolder(itemView) {

    private lateinit var workoutEntity: WorkoutEntity

    private val resources = itemView.resources

    private val dayTextView : TextView = itemView.findViewById(R.id.viewholder_program_detail_workout_day)

    private val nameTextView: TextView
            = itemView.findViewById(R.id.viewholder_program_detail_workout_name)

    private val exercisesTextView: TextView
            = itemView.findViewById(R.id.viewholder_program_detail_workout_exercises)

    init {
        itemView.setOnClickListener {
            itemClickListener.onItemClicked(workoutEntity.recordId!!)
        }
    }

    fun set(workoutEntity: WorkoutEntity, position: Int) {
        this.workoutEntity = workoutEntity

        val exercises = resources.getQuantityString(R.plurals.exercises_format,
            workoutEntity.exercises.size, workoutEntity.exercises.size)

        dayTextView.text = resources.getString(R.string.day_format, position + 1)

        nameTextView.text = workoutEntity.name
        exercisesTextView.text = exercises

    }

}