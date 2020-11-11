package com.gerosprime.gylog.models.workouts.runningsession.load

import com.gerosprime.gylog.models.exercises.performed.ExercisePerformedEntity
import com.gerosprime.gylog.models.workouts.runningsession.WorkoutSessionEntity

class WorkoutSessionCacheLoadResult(val workoutEntityId : Long,
                                    val workoutSessionEntity: WorkoutSessionEntity,
                                    val prePerformedExercises: ArrayList<ExercisePerformedEntity>)