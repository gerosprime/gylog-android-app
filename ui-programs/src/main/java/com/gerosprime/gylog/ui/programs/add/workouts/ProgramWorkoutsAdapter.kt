package com.gerosprime.gylog.ui.programs.add.workouts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.workouts.WorkoutEntity
import com.gerosprime.gylog.ui.programs.R

internal class ProgramWorkoutsAdapter(var items : ArrayList<WorkoutEntity>)
    : RecyclerView.Adapter<ProgramWorkoutsViewHolder>() {

    var layoutInflater : LayoutInflater? = null

    var exerciseWorkoutListener : OnItemClickListener<Int>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramWorkoutsViewHolder {

        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)

        var inflated = layoutInflater!!.
            inflate(R.layout.viewholder_program_workouts, parent, false)

        return ProgramWorkoutsViewHolder(inflated, exerciseWorkoutListener)

    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ProgramWorkoutsViewHolder, position: Int) {
        holder.set(items[position], position)
    }

    fun refreshWorkoutContent(workoutIndex : Int) {
        notifyItemChanged(workoutIndex)
    }

}