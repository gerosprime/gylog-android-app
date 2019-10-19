package com.gerosprime.gylog.models

import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.models.exercises.ExercisePerformedEntity
import com.gerosprime.gylog.models.exercises.ExerciseTemplateEntity
import com.gerosprime.gylog.models.exercises.PerformedSetEntity
import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.models.workouts.WorkoutEntity

class ModelsCache (val programsMap : Map<Long, ProgramEntity>,
                   val workouts : Map<Long, WorkoutEntity>,
                   val exercises : Map<Long, ExerciseEntity>,
                   val templateExercises : Map<Long, ExerciseTemplateEntity>,
                   val performedExercises : Map<Long, ExercisePerformedEntity>,
                   val performedSets : Map<Long, PerformedSetEntity>)