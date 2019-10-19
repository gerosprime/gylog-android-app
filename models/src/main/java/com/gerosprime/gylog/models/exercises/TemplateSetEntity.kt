package com.gerosprime.gylog.models.exercises

import androidx.room.PrimaryKey

data class TemplateSetEntity(
    @PrimaryKey override val recordId: Long,
    val minReps : Int,
    override val reps: Int,
    override val weight: Float,
    override val counterWeight: Float,
    override val durationSeconds: Int,
    val restTimeSeconds : Int
) : SetEntity