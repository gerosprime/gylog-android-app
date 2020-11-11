package com.gerosprime.gylog.models.workouts.history

import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.models.exercises.performed.ExercisePerformedEntity

class WorkoutExerciseHistoryResult (val workoutSessionId : Long,
                                    val workoutExerciseId : Long,
                                    val exercise : ExerciseEntity,
                                    val exercises : ArrayList<ExercisePerformedEntity>)