package com.gerosprime.gylog.models.workouts

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gerosprime.gylog.models.exercises.templates.ExerciseTemplateEntity

@Entity
data class WorkoutEntity(@PrimaryKey var recordId: Long? = null,
                         var name:String? = null,
                         var description:String? = null,
                         var programId : Long? = null,
                         var day : Int? = null,
                         var exercises : ArrayList<ExerciseTemplateEntity>,
                         var totalTimeSeconds : Int? = null) {
    fun deepCopy(): WorkoutEntity {

        val workoutCopy = WorkoutEntity(recordId, name, description, programId, day,
            arrayListOf(), totalTimeSeconds)

        for (exercise in exercises) {
            workoutCopy.exercises.add(exercise.deepCopy())
        }

        return workoutCopy
    }
}