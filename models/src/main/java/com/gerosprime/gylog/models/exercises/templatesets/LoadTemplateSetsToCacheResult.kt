package com.gerosprime.gylog.models.exercises.templatesets

import com.gerosprime.gylog.models.exercises.templates.ExerciseTemplateEntity

class LoadTemplateSetsToCacheResult(
    val workoutIndex: Int,
    val workoutExercisesIndex: Int,
    val exerciseTemplateEntity: ExerciseTemplateEntity,
    val copyTemplates: ArrayList<TemplateSetEntity>,
    val totalWeight : Float,
    val totalSets : Int,
    val totalRestDuration : Int
)