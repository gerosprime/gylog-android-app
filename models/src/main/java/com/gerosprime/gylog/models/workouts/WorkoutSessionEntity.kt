package com.gerosprime.gylog.models.workouts

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gerosprime.gylog.models.exercises.ExercisePerformedEntity

@Entity
data class WorkoutSessionEntity (@PrimaryKey val recordId : Long,
                                 val workoutId : Long,
                                 val exercisesPerformed : List<ExercisePerformedEntity>)