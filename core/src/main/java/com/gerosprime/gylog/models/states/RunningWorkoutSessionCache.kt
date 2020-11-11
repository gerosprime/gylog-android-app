package com.gerosprime.gylog.models.states

import com.gerosprime.gylog.models.exercises.performed.ExercisePerformedEntity
import com.gerosprime.gylog.models.workouts.runningsession.WorkoutSessionEntity
import java.util.*
import kotlin.collections.ArrayList

class RunningWorkoutSessionCache (var workoutSessionEntity: WorkoutSessionEntity?,
                                  var prePerformedExercises: ArrayList<ExercisePerformedEntity>?)