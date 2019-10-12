package com.gerosprime.gylog.ui.exercises.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.ui.exercises.R

class DashboardExercisesAdapter(var exercises: List<ExerciseEntity>) :
    RecyclerView.Adapter<DashboardExercisesViewHolder>() {

    var inflater : LayoutInflater? = null

    var listener : OnItemClickListener<ExerciseEntity>? = null

    private val internalListener : OnItemClickListener<ExerciseEntity> =
        object : OnItemClickListener<ExerciseEntity> {
            override fun onItemClicked(item: ExerciseEntity) {
                listener?.onItemClicked(item)
            }
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardExercisesViewHolder {

        if (inflater == null)
            inflater = LayoutInflater.from(parent.context)

        val inflated = inflater!!.inflate(R.layout.viewholder_dashboard_exercises,
            parent, false)

        return DashboardExercisesViewHolder(inflated, internalListener)
    }

    override fun getItemCount(): Int = exercises.size

    override fun onBindViewHolder(holder: DashboardExercisesViewHolder, position: Int) {
        holder.set(exercises[position])
    }
}
