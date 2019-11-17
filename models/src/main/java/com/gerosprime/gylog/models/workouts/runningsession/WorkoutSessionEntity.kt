package com.gerosprime.gylog.models.workouts.runningsession

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.gerosprime.gylog.models.exercises.performed.ExercisePerformedEntity
import java.util.*

@Entity
data class WorkoutSessionEntity (@PrimaryKey var recordId : Long? = null,
                                 @ColumnInfo(index = true) val workoutId : Long,
                                 var durationSeconds : Long = 0,
                                 var startDate : Date? = null,
                                 var endDate : Date? = null) {
    @Ignore
    var exercisesPerformed : ArrayList<ExercisePerformedEntity> = arrayListOf()

}