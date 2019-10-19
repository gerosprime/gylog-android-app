package com.gerosprime.gylog.models.exercises

import com.gerosprime.gylog.models.workouts.WorkoutEntity

class ExerciseTemplatesAddToCacheResult (val workoutEntity: WorkoutEntity,
                                         val exerciseTemplates : List<ExerciseTemplateEntity>,
                                         val startIndex : Int, val endIndex : Int)