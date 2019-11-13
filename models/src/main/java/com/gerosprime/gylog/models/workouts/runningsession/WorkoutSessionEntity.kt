package com.gerosprime.gylog.models.workouts.runningsession

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gerosprime.gylog.models.exercises.performed.ExercisePerformedEntity
import java.util.*
import kotlin.collections.ArrayList

@Entity
data class WorkoutSessionEntity (@PrimaryKey var recordId : Long? = null,
                                 val workoutId : Long,
                                 var exercisesPerformed : ArrayList<ExercisePerformedEntity>?,
                                 var durationSeconds : Long = 0,
                                 var startDate : Date? = null,
                                 var endDate : Date? = null)