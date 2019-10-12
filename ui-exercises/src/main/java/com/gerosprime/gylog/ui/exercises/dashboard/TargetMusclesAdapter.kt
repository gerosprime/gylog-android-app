package com.gerosprime.gylog.ui.exercises.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.models.muscle.MuscleEnum
import com.gerosprime.gylog.ui.exercises.R

internal class TargetMusclesAdapter(var muscles : List<MuscleEnum>)
    : RecyclerView.Adapter<TargetMuscleViewHolder>() {

    var layoutInflater : LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TargetMuscleViewHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)

        var inflated = layoutInflater!!.inflate(R.layout.viewholder_dashboard_exercises_muscles,
            parent, false)

        return TargetMuscleViewHolder(inflated)
    }

    override fun getItemCount(): Int = muscles.size

    override fun onBindViewHolder(holder: TargetMuscleViewHolder, position: Int) {
        holder.set(muscles[position])
    }
}