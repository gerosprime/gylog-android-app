package com.gerosprime.gylog.ui.workouts.detail.adapter

import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.exercises.templates.ExerciseTemplateEntity
import com.gerosprime.gylog.ui.workouts.R
import com.gerosprime.gylog.ui.workouts.databinding.ViewholderWorkoutDetailExercisesBinding

class WorkoutDetailViewHolder(private val clickListener: OnItemClickListener<WorkoutExerciseClick>,
                              itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    private lateinit var exerciseTemplateEntity: ExerciseTemplateEntity

    private val binding = ViewholderWorkoutDetailExercisesBinding.bind(itemView)

    private val resources  = itemView.resources

    init {
        binding.apply {
            viewholderWorkoutDetailExerciseMore.setOnClickListener {
                val popupMenu = PopupMenu(itemView.context, viewholderWorkoutDetailExerciseMore)
                popupMenu.menuInflater.inflate(R.menu.menu_viewholder_performed_set_menu,
                    popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { menuClicked(it) }
                popupMenu.show()
            }

            viewholderWorkoutDetailExerciseContent.setOnClickListener {
                clickListener
                    .onItemClicked(WorkoutExerciseClick(exerciseTemplateEntity))
            }

        }

    }

    private fun menuClicked(menuItem: MenuItem) : Boolean {
        return true
    }

    fun set(exerciseTemplateEntity: ExerciseTemplateEntity) {
        this.exerciseTemplateEntity = exerciseTemplateEntity

        binding.apply {
            viewholderWorkoutDetailExerciseName.text = exerciseTemplateEntity.name
            viewholderWorkoutDetailSets.text = resources.getQuantityString(R.plurals.sets_format,
                exerciseTemplateEntity.setTemplates.size,
                exerciseTemplateEntity.setTemplates.size)
        }




    }

}