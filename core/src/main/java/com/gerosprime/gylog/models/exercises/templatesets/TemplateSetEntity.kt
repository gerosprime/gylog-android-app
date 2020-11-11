package com.gerosprime.gylog.models.exercises.templatesets

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.gerosprime.gylog.models.exercises.SetEntity
import com.gerosprime.gylog.models.exercises.templates.ExerciseTemplateEntity
import com.gerosprime.gylog.models.workouts.WorkoutEntity

@Entity(foreignKeys = [
    ForeignKey(entity = ExerciseTemplateEntity::class,
        parentColumns = ["recordId"], childColumns = ["exerciseTemplateId"],
        onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)])
data class TemplateSetEntity(
    @PrimaryKey override var recordId: Long? = null,
    @ColumnInfo(index = true) var exerciseTemplateId : Long? = null,
    var minReps : Int? = null,
    override var reps: Int? = null,
    override var weight: Float? = 0f,
    override var counterWeight: Float? = 0f,
    override var durationSeconds: Int = 0,
    override var restTimeSeconds : Int = 0,
    override var upToFailure :Boolean = false,
    var markDeleteOnRecord : Boolean = false,
    override var exerciseId: Long? = null

) : SetEntity {
    fun deepCopy(): TemplateSetEntity {
        return TemplateSetEntity(
            recordId,exerciseTemplateId,  minReps, reps,
            weight, counterWeight, durationSeconds, restTimeSeconds, upToFailure,
            markDeleteOnRecord)
    }
}