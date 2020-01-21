package com.gerosprime.gylog.ui.exercises.dashboard

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.ui.exercises.R

class DashboardExercisesViewHolder(
    itemView: View,
    private var listener: OnItemClickListener<ExerciseItemClick>
)
    : RecyclerView.ViewHolder(itemView) {

    private var titleTextView : TextView = itemView.findViewById(R.id.viewholder_dashboard_name)
    private var imageTextView : ImageView = itemView.findViewById(R.id.viewholder_dashboard_photo)
    private var targetMusclesRecyclerView : RecyclerView = itemView.findViewById(R.id.viewholder_dashboard_muscles)

    init {
        targetMusclesRecyclerView.layoutManager = LinearLayoutManager(
                    itemView.context,
                    LinearLayoutManager.HORIZONTAL, false)

        itemView.setOnClickListener {
            listener.onItemClicked(ExerciseItemClick(layoutPosition, exerciseItem))
        }
    }

    private lateinit var exerciseItem : ExerciseEntity

    fun set(exerciseEntity: ExerciseEntity) {
        this.exerciseItem = exerciseEntity

        titleTextView.text = exerciseEntity.name

        Glide.with(itemView).load(R.color.colorPrimary)
            .into(imageTextView)

        // TODO Image
        var adapter = targetMusclesRecyclerView.adapter as TargetMusclesAdapter?
        if (adapter != null) {
            adapter.muscles = exerciseEntity.targetMuscles
            adapter.notifyDataSetChanged()
        }
        else
            targetMusclesRecyclerView.adapter = TargetMusclesAdapter(exerciseItem.targetMuscles)


    }

}