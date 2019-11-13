package com.gerosprime.gylog.models.exercises.templatesets

import androidx.room.PrimaryKey
import com.gerosprime.gylog.models.exercises.SetEntity

data class TemplateSetEntity(
    @PrimaryKey override var recordId: Long? = null,
    var minReps : Int? = null,
    override var reps: Int? = null,
    override var weight: Float? = 0f,
    override var counterWeight: Float? = 0f,
    override var durationSeconds: Int = 0,
    override var restTimeSeconds : Int = 0,
    override var upToFailure :Boolean = false
) : SetEntity {
    fun deepCopy(): TemplateSetEntity {
        return TemplateSetEntity(
            recordId, minReps, reps,
            weight, counterWeight, durationSeconds, restTimeSeconds, upToFailure
        )
    }
}