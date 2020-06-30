package com.gerosprime.gylog.ui.exercises.dashboard

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.models.muscle.MuscleEnum
import com.gerosprime.gylog.ui.exercises.databinding.ViewholderExerciseMuscleBinding
import com.google.android.material.chip.Chip

class TargetMuscleViewHolder(itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    private val binding = ViewholderExerciseMuscleBinding.bind(itemView)

    private lateinit var muscle : MuscleEnum

    @SuppressLint("ResourceType")
    fun set(muscleEnum: MuscleEnum) {
        this.muscle = muscleEnum
        binding.root.setText(muscleEnum.stringResId)
    }
}