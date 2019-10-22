package com.gerosprime.gylog.models.exercises.performedsets

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gerosprime.gylog.models.exercises.SetEntity

@Entity
data class PerformedSetEntity(
    @PrimaryKey override var recordId: Long?,
    override var reps: Int,
    override var weight: Float,
    override var counterWeight: Float,
    override var durationSeconds: Int
) : SetEntity