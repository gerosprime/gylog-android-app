package com.gerosprime.gylog.ui.workouts.exercises

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.base.components.android.CheckableRelativeLayout
import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.ui.workouts.R


class WorkoutExerciseEditViewHolder(
        itemView : View,
        private val selectedItems : MutableMap<Long, ExerciseEntity>,
        private val imageClickListener: OnItemClickListener<ExerciseEntity>)
            : RecyclerView.ViewHolder(itemView) {

    lateinit var entity : ExerciseEntity

    private val checkableItemView = itemView as CheckableRelativeLayout

    private var nameTextView : TextView
            = itemView.findViewById(R.id.viewholder_exercise_add_name)
    private var imageExercise : ImageView
            = itemView.findViewById(R.id.viewholder_exercise_add_photo)

    private var checkBox : CheckBox
            = itemView.findViewById(R.id.viewholder_exercise_add_checkbox)

    init {

        imageExercise.setOnClickListener {
            imageClickListener.onItemClicked(entity)
        }

        checkableItemView.setOnClickListener {
            checkableItemView.toggle()
            onCheck(checkableItemView.isChecked)
            updateCheckBox(checkableItemView.isChecked)

        }

        checkBox.setOnCheckedChangeListener {
                _, b ->
            run {
                updateItemViewCheck(b)
                onCheck(b)
            }
        }
    }

    private fun updateItemViewCheck(check: Boolean) {
        checkableItemView.isChecked = check
    }

    private fun updateCheckBox (check : Boolean) {
        checkBox.setOnCheckedChangeListener(null)
        checkBox.isChecked = check

        checkBox.setOnCheckedChangeListener {
                _, b ->
            run {
                updateItemViewCheck(b)
                onCheck(b)
            }
        }
    }

    private fun onCheck (check : Boolean) {
        if (check)
            selectedItems[entity.recordId!!] = entity
        else
            selectedItems.remove(entity.recordId)
    }

    fun set(entity: ExerciseEntity) {
        this.entity = entity


        nameTextView.text = entity.name

        var selected =
            selectedItems.containsKey(entity.recordId)

        updateCheckBox(selected)
        updateItemViewCheck(selected)

    }

}