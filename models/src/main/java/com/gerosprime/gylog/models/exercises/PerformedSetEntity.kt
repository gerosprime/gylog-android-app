package com.gerosprime.gylog.models.exercises

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PerformedSetEntity(
    @PrimaryKey override val recordId: Long,
    override val reps: Int,
    override val weight: Float,
    override val counterWeight: Float,
    override val durationSeconds: Int
) : SetEntity