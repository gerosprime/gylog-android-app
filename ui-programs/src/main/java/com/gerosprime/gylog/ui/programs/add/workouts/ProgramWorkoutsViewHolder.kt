package com.gerosprime.gylog.ui.programs.add.workouts

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.models.workouts.WorkoutEntity
import com.gerosprime.gylog.ui.programs.R
import com.gerosprime.gylog.ui.programs.add.exercises.ExerciseExecutionAdapter

internal class ProgramWorkoutsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    private var workoutEntity : WorkoutEntity? = null

    private var exercisesRecyclerView: RecyclerView
            = itemView.findViewById(R.id.viewholder_program_workouts_exercises)
    private var titleTextView : TextView = itemView.findViewById(R.id.viewholder_program_workouts_title)
    private var subTitleTextView : TextView = itemView.findViewById(R.id.viewholder_program_workouts_subtitle)

    private var context = itemView.context

    init {
        exercisesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    fun set(workoutEntity : WorkoutEntity) {
        this.workoutEntity = workoutEntity

        // TODO Cache adapters
        exercisesRecyclerView.adapter = ExerciseExecutionAdapter(workoutEntity.exercises!!)
        titleTextView.text = workoutEntity.name
        subTitleTextView.text = context.getString(R.string.day_format, workoutEntity.day)


    }

}