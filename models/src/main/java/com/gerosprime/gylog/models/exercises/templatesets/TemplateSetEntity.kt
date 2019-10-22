package com.gerosprime.gylog.models.exercises.templatesets

import androidx.room.PrimaryKey
import com.gerosprime.gylog.models.exercises.SetEntity

data class TemplateSetEntity(
    @PrimaryKey override var recordId: Long? = null,
    var minReps : Int = 0,
    override var reps: Int = 0,
    override var weight: Float = 0f,
    override var counterWeight: Float = 0f,
    override var durationSeconds: Int = 0,
    var restTimeSeconds : Int = 30,
    var upToFailure :Boolean = false
) : SetEntity {
    fun deepCopy(): TemplateSetEntity {
        return TemplateSetEntity(
            recordId, minReps, reps,
            weight, counterWeight, durationSeconds, restTimeSeconds, upToFailure
        )
    }
}