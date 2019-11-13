package com.gerosprime.gylog.ui.workouts.session.adapters.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.exercises.performed.ExercisePerformedEntity
import com.gerosprime.gylog.ui.workouts.R


internal class PerformedExerciseAdapter
    (private val performedExercises: List<ExercisePerformedEntity>,
     private val setClickListener: OnItemClickListener<PerformedSetClick>,
     private var addItemClick : OnItemClickListener<AddPerformSetClick>,
     private var removeItemClick : OnItemClickListener<RemovePerformSetClick>,
     private var unRemovetemClick : OnItemClickListener<UnRemovePerformSetClick>) :
    RecyclerView.Adapter<PerformedExerciseViewHolder>() {

    private var layoutInflater : LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerformedExerciseViewHolder {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.context)

        val inflated = layoutInflater!!.inflate(
            R.layout.viewholder_workout_session_exercises,
            parent, false)

        return PerformedExerciseViewHolder(inflated,
            setClickListener, addItemClick,
            removeItemClick, unRemovetemClick)
    }

    override fun getItemCount(): Int = performedExercises.size

    override fun onBindViewHolder(
        holder: PerformedExerciseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            holder.set(performedExercises[position], position)
        } else {
            holder.update(performedExercises[position], position, payloads)
        }
    }

    override fun onBindViewHolder(holder: PerformedExerciseViewHolder, position: Int) {
        holder.set(performedExercises[position], position)
    }

}