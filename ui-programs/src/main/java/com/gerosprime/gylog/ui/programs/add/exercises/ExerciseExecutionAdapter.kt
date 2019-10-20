package com.gerosprime.gylog.ui.programs.add.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.models.exercises.ExerciseTemplateEntity
import com.gerosprime.gylog.ui.programs.R

internal class ExerciseExecutionAdapter(var exercises: List<ExerciseTemplateEntity>)
    : RecyclerView.Adapter<ExerciseExecutionViewHolder>() {

    private var layoutInflater : LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseExecutionViewHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)

        var inflated = layoutInflater!!.
            inflate(R.layout.viewholder_workout_exercise_execs, parent, false)

        return ExerciseExecutionViewHolder(inflated)
    }

    override fun getItemCount(): Int = exercises.size

    override fun onBindViewHolder(holder: ExerciseExecutionViewHolder, position: Int) {
        holder.set(exercises[position])
    }
}