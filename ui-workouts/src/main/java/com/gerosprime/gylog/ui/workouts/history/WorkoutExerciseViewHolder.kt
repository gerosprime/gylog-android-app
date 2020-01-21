package com.gerosprime.gylog.ui.workouts.history

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.models.exercises.performed.ExercisePerformedEntity
import com.gerosprime.gylog.ui.workouts.R
import java.text.DateFormat


class WorkoutExerciseViewHolder(private val performedDateFormat : DateFormat,
                                itemView: View) : RecyclerView.ViewHolder(itemView) {


    private lateinit var entity : ExercisePerformedEntity

    private var titleTextView : TextView
            = itemView.findViewById(R.id.viewholder_workout_exercise_history_title)

    private var recyclerViewSets: RecyclerView
            = itemView.findViewById(R.id.viewholder_workout_exercise_history_sets)

    fun set(entity : ExercisePerformedEntity) {
        this.entity = entity

        titleTextView.text = performedDateFormat.format(entity.performedDate)
        recyclerViewSets.adapter = WorkoutExerciseHistorySetAdapter(entity.performedSets)

    }

}