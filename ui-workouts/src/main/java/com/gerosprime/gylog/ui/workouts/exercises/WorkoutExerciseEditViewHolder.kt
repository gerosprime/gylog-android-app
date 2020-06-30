package com.gerosprime.gylog.ui.workouts.exercises

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.base.OnItemClickListener
import com.gerosprime.gylog.base.components.android.CheckableRelativeLayout
import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.ui.exercises.dashboard.TargetMusclesAdapter
import com.gerosprime.gylog.ui.workouts.R
import com.gerosprime.gylog.ui.workouts.databinding.ViewholderWorkoutExerciseAddBinding


class WorkoutExerciseEditViewHolder(
        itemView : View,
        private val selectedItems : MutableMap<Long, ExerciseEntity>,
        private val imageClickListener: OnItemClickListener<ExerciseEntity>)
            : RecyclerView.ViewHolder(itemView) {

    private lateinit var entity : ExerciseEntity

    private val binding = ViewholderWorkoutExerciseAddBinding.bind(itemView)
    private val checkableItemView = binding.root

    init {

        binding.apply {

            viewholderWorkoutExercisesMuscles.adapter = TargetMusclesAdapter(listOf())
            viewholderWorkoutExercisesMuscles.layoutManager = LinearLayoutManager(itemView.context,
                LinearLayoutManager.HORIZONTAL, false)

            viewholderExerciseAddPhoto.setOnClickListener {
                imageClickListener.onItemClicked(entity)
            }


            checkableItemView.setOnClickListener {
                checkableItemView.toggle()
                onCheck(checkableItemView.isChecked)
                updateCheckBox(checkableItemView.isChecked)

            }

            viewholderExerciseAddCheckbox.setOnCheckedChangeListener { _, b ->
                run {
                    updateItemViewCheck(b)
                    onCheck(b)
                }
            }
        }

    }

    private fun updateItemViewCheck(check: Boolean) {
        checkableItemView.isChecked = check
    }

    private fun updateCheckBox (check : Boolean) {

        binding.apply {
            viewholderExerciseAddCheckbox.setOnCheckedChangeListener(null)
            viewholderExerciseAddCheckbox.isChecked = check

            viewholderExerciseAddCheckbox.setOnCheckedChangeListener {
                    _, b ->
                run {
                    updateItemViewCheck(b)
                    onCheck(b)
                }
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


        binding.apply {

            viewholderExerciseAddName.text = entity.name

            val adapter = viewholderWorkoutExercisesMuscles.adapter as TargetMusclesAdapter
            adapter.muscles = entity.targetMuscles
            adapter.notifyItemRangeChanged(0, entity.targetMuscles.size)

        }

        val selected =
            selectedItems.containsKey(entity.recordId)

        updateCheckBox(selected)
        updateItemViewCheck(selected)

    }

}