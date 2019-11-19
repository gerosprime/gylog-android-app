package com.gerosprime.gylog.models.workouts

import androidx.room.*
import com.gerosprime.gylog.models.exercises.templates.ExerciseTemplateEntity
import com.gerosprime.gylog.models.programs.ProgramEntity

@Entity(foreignKeys = [
    ForeignKey(entity = ProgramEntity::class,
        parentColumns = arrayOf("recordId"), childColumns = arrayOf("programId"),
        onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)]
)
data class WorkoutEntity(
    @PrimaryKey var recordId: Long? = null,
    var name: String? = null,
    var description: String? = null,
    @ColumnInfo(index = true)
    var programId: Long? = null,
    var day: Int? = null,
    var totalTimeSeconds: Int? = null

) {

    @Ignore var exercises : ArrayList<ExerciseTemplateEntity> = arrayListOf()
    @Ignore var deleteExercises: ArrayList<ExerciseTemplateEntity> = arrayListOf()

    fun deepCopy(): WorkoutEntity {

        val workoutCopy = WorkoutEntity(
            recordId,
            name,
            description,
            programId,
            day,
            totalTimeSeconds)

        for (exercise in exercises) {
            workoutCopy.exercises.add(exercise.deepCopy())
        }

        for (deleteExercise in deleteExercises) {
            workoutCopy.deleteExercises.add(deleteExercise.deepCopy())
        }

        return workoutCopy
    }
}