package com.gerosprime.gylog.ui.workouts.session.adapters.exercises

import com.gerosprime.gylog.models.exercises.performed.ExercisePerformedEntity
import com.gerosprime.gylog.models.exercises.performedsets.PerformedSetEntity

class PerformedSetClick (val exerciseIndex: Int, val setIndex : Int,
                         val performedExercise : ExercisePerformedEntity,
                         val performedSet: PerformedSetEntity)