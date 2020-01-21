package com.gerosprime.gylog.ui.workouts.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.models.exercises.performedsets.PerformedSetEntity
import com.gerosprime.gylog.ui.workouts.R

class WorkoutExerciseHistorySetAdapter(private val sets : ArrayList<PerformedSetEntity>)
    : RecyclerView.Adapter<WorkoutExerciseHistorySetVH>() {

    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutExerciseHistorySetVH {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        return WorkoutExerciseHistorySetVH(
            layoutInflater!!.inflate(R.layout.viewholder_workout_exercise_history_sets,
                parent, false))
    }

    override fun getItemCount(): Int = sets.size

    override fun onBindViewHolder(holder: WorkoutExerciseHistorySetVH, position: Int) {
        holder.set(sets[position])
    }

}