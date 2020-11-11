package com.gerosprime.gylog.models.workouts.runningsession.create

import com.gerosprime.gylog.models.exercises.performed.ExercisePerformedEntity
import com.gerosprime.gylog.models.workouts.runningsession.WorkoutSessionEntity

class WorkoutSessionCreationResult (val workoutEntityId : Long,
                                    val workoutSessionEntity: WorkoutSessionEntity,
                                    val prePerformedExercises: ArrayList<ExercisePerformedEntity>)