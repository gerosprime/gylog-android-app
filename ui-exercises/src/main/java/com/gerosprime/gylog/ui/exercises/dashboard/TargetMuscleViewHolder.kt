package com.gerosprime.gylog.ui.exercises.dashboard

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gerosprime.gylog.models.muscle.MuscleEnum

internal class TargetMuscleViewHolder(itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    private val muscleNameTextView = itemView as TextView

    private lateinit var muscle : MuscleEnum

    @SuppressLint("ResourceType")
    fun set(muscleEnum: MuscleEnum) {
        this.muscle = muscleEnum
        muscleNameTextView.setText(muscleEnum.stringResId)
    }
}