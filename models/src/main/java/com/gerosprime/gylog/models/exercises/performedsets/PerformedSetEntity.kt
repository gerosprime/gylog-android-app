package com.gerosprime.gylog.models.exercises.performedsets

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gerosprime.gylog.models.exercises.SetEntity
import java.util.*

@Entity
data class PerformedSetEntity(
    @PrimaryKey override var recordId: Long?,
    override var reps: Int? = null,
    override var weight: Float? = null,
    override var counterWeight: Float? = null,
    override var durationSeconds: Int,
    override var restTimeSeconds: Int,
    override var upToFailure: Boolean,
    var templateSetId: Long?,
    var initialReps: Int?,
    var initialWeight: Float?,
    var previousPerformedSetId: Long? = null,
    var previousReps: Int? = null,
    var previousWeight: Float? = null,
    var flagRemoved: Boolean = false,
    var datePerformed: Date?,
    var exercisePerformedId: Long? = null,
    override var exerciseId: Long?
) : SetEntity {

    val isFreeSet get() = templateSetId == null

}