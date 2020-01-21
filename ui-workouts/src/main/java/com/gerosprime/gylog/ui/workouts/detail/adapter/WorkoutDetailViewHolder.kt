package com.gerosprime.gylog.ui.workouts.detail.adapter

import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.models.exercises.templates.ExerciseTemplateEntity
import com.gerosprime.gylog.ui.workouts.R

class WorkoutDetailViewHolder(private val clickListener
                              : OnItemClickListener<WorkoutExerciseClick>,
                              itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    private lateinit var exerciseTemplateEntity: ExerciseTemplateEntity

    private val content : View = itemView.findViewById(R.id.viewholder_workout_detail_exercise_content)

    private val exerciseNameTextView : TextView
            = itemView.findViewById(R.id.viewholder_workout_detail_exercise_name)
    private val setsCountTextView : TextView
            = itemView.findViewById(R.id.viewholder_workout_detail_sets)
    private val exerciseImage : ImageView
            = itemView.findViewById(R.id.viewholder_workout_detail_exercise_image)
    private val moreButton : ImageView
            = itemView.findViewById(R.id.viewholder_workout_detail_exercise_more)

    private val resources  = itemView.resources

    init {
        moreButton.setOnClickListener {
            val popupMenu = PopupMenu(itemView.context, moreButton)
            popupMenu.menuInflater.inflate(R.menu.menu_viewholder_performed_set_menu,
                popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuClicked(it) }
            popupMenu.show()
        }
    }

    private fun menuClicked(menuItem: MenuItem) : Boolean {
        return true
    }

    fun set(exerciseTemplateEntity: ExerciseTemplateEntity) {
        this.exerciseTemplateEntity = exerciseTemplateEntity

        content.setOnClickListener {
            clickListener
                .onItemClicked(WorkoutExerciseClick(exerciseTemplateEntity))
        }

        exerciseNameTextView.text = exerciseTemplateEntity.name
        setsCountTextView.text = resources.getQuantityString(R.plurals.sets_format,
            exerciseTemplateEntity.setTemplates.size,
            exerciseTemplateEntity.setTemplates.size)

    }

}