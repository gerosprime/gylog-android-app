package com.gerosprime.gylog.ui.programs.add.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.exercises.templates.ExerciseTemplateEntity
import com.gerosprime.gylog.ui.programs.R

internal class ExerciseExecutionAdapter(
    var exercises: List<ExerciseTemplateEntity>,
    var workoutIndex: Int,
    private var exerciseClickListener: OnItemClickListener<ExerciseExecutionClicked>? = null
)
    : RecyclerView.Adapter<ExerciseExecutionViewHolder>() {

    private var layoutInflater : LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseExecutionViewHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)

        val inflated = layoutInflater!!.
            inflate(R.layout.viewholder_workout_exercise_execs, parent, false)

        return ExerciseExecutionViewHolder(inflated, exerciseClickListener)
    }

    override fun getItemCount(): Int = exercises.size

    override fun onBindViewHolder(holder: ExerciseExecutionViewHolder, position: Int) {
        holder.set(exercises[position], workoutIndex, position)
    }
}
