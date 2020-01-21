package com.gerosprime.gylog.ui.workouts.history

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.models.exercises.performedsets.PerformedSetEntity
import com.gerosprime.gylog.ui.workouts.R

class WorkoutExerciseHistorySetVH(itemView: View) : RecyclerView.ViewHolder(itemView) {


    private val indexTextView : TextView
            = itemView.findViewById(R.id.viewholder_workout_exercise_history_set_index)
    private val repWeightTextView : TextView
            = itemView.findViewById(R.id.viewholder_workout_exercise_history_set_repweight)

    lateinit var entity : PerformedSetEntity


    @SuppressLint("SetTextI18n")
    fun set(entity : PerformedSetEntity) {
        this.entity = entity

        indexTextView.text = "${adapterPosition + 1}"

        repWeightTextView.text =
            itemView.context.getString(R.string.workout_history_repweight_format,
                entity.weight, entity.reps)

    }


}