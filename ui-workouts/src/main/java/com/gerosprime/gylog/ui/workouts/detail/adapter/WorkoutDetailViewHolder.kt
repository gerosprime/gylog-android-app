package com.gerosprime.gylog.ui.workouts.detail.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.models.exercises.templates.ExerciseTemplateEntity
import com.gerosprime.gylog.ui.workouts.R

class WorkoutDetailViewHolder(itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    private lateinit var exerciseTemplateEntity: ExerciseTemplateEntity

    private val exerciseNameTextView : TextView
            = itemView.findViewById(R.id.viewholder_workout_detail_exercise_name)
    private val setsCountTextView : TextView
            = itemView.findViewById(R.id.viewholder_workout_detail_sets)
    private val exerciseImage : ImageView
            = itemView.findViewById(R.id.viewholder_workout_detail_exercise_image)

    private val resources  = itemView.resources

    fun set(exerciseTemplateEntity: ExerciseTemplateEntity) {
        this.exerciseTemplateEntity = exerciseTemplateEntity

        exerciseNameTextView.text = exerciseTemplateEntity.name
        setsCountTextView.text = resources.getQuantityString(R.plurals.sets_format,
            exerciseTemplateEntity.setTemplates.size,
            exerciseTemplateEntity.setTemplates.size)

    }

}