package com.gerosprime.gylog.ui.workouts.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.models.exercises.performed.ExercisePerformedEntity
import com.gerosprime.gylog.ui.workouts.R
import java.text.DateFormat

class WorkoutExerciseHistoryAdapter(
    private val performedDateFormat : DateFormat,
    private val exercises : ArrayList<ExercisePerformedEntity>)
    : RecyclerView.Adapter<WorkoutExerciseViewHolder>() {

    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutExerciseViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val inflated = layoutInflater!!.inflate(R.layout.viewholder_workout_exercise_history,
            parent, false)

        return WorkoutExerciseViewHolder(performedDateFormat, inflated)
    }

    override fun getItemCount(): Int = exercises.size

    override fun onBindViewHolder(holder: WorkoutExerciseViewHolder, position: Int) {
        holder.set(exercises[position])
    }
}