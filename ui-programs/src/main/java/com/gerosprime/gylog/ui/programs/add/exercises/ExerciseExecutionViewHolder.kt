package com.gerosprime.gylog.ui.programs.add.exercises

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.models.exercises.ExerciseTemplateEntity
import com.gerosprime.gylog.ui.programs.R

internal class ExerciseExecutionViewHolder(itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    private var imageView : ImageView = itemView.findViewById(R.id.viewholder_workout_exercise_execs_image)
    private var nameTextView : TextView = itemView.findViewById(R.id.viewholder_workout_exercise_execs_name)
    private var setsTextView : TextView = itemView.findViewById(R.id.viewholder_workout_exercise_execs_sets)

    private var exercise : ExerciseTemplateEntity? = null

    private var context = itemView.context

    fun set(exercise : ExerciseTemplateEntity?) {
        this.exercise = exercise

        nameTextView.text = exercise!!.name

        val setsFormat : String = context.resources.getQuantityText(R.plurals.sets_format,
            exercise.setTemplates.size).toString()

        setsTextView.text = String.format(setsFormat, exercise.setTemplates.size)

    }

}
