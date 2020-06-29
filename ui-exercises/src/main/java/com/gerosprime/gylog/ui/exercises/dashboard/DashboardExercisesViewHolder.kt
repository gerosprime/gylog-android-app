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
import com.gerosprime.gylog.ui.exercises.databinding.ViewholderDashboardExercisesBinding

class DashboardExercisesViewHolder(
    itemView: View,
    private var listener: OnItemClickListener<ExerciseItemClick>
)
    : RecyclerView.ViewHolder(itemView) {


    private val binding = ViewholderDashboardExercisesBinding.bind(itemView)

    init {
        binding.apply {
            viewholderDashboardMuscles.layoutManager = LinearLayoutManager(
                itemView.context,
                LinearLayoutManager.HORIZONTAL, false)

            viewholderDashboardMuscles.adapter = TargetMusclesAdapter(mutableListOf())
        }

        itemView.setOnClickListener {
            listener.onItemClicked(ExerciseItemClick(layoutPosition, exerciseItem))
        }
    }

    private lateinit var exerciseItem : ExerciseEntity

    fun bind(exerciseEntity: ExerciseEntity) {
        this.exerciseItem = exerciseEntity

        binding.apply {
            viewholderDashboardName.text = exerciseEntity.name

            Glide.with(itemView).load(R.color.colorPrimary)
                .into(viewholderDashboardPhoto)

            val adapter = viewholderDashboardMuscles.adapter as TargetMusclesAdapter
            adapter.muscles = exerciseEntity.targetMuscles
            adapter.notifyDataSetChanged()

        }

    }

}