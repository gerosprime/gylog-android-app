package com.gerosprime.gylog.ui.workouts.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.models.exercises.templates.ExerciseTemplateEntity
import com.gerosprime.gylog.ui.workouts.R

class WorkoutDetailAdapter(private val exercises : List<ExerciseTemplateEntity>)
    : RecyclerView.Adapter<WorkoutDetailViewHolder>() {

    private var layoutInflater : LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutDetailViewHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)

        val inflated = layoutInflater!!.inflate(R.layout.viewholder_workout_detail_exercises, parent, false)

        return WorkoutDetailViewHolder(inflated)

    }

    override fun getItemCount(): Int = exercises.size

    override fun onBindViewHolder(holder: WorkoutDetailViewHolder, position: Int) {
        holder.set(exercises[position])
    }
}