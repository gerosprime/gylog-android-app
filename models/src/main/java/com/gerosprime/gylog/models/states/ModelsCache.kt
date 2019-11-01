package com.gerosprime.gylog.models.states

import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.models.exercises.performed.ExercisePerformedEntity
import com.gerosprime.gylog.models.exercises.templates.ExerciseTemplateEntity
import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.models.workouts.WorkoutEntity

class ModelsCache (val programsMap : MutableMap<Long, ProgramEntity>,
                   val programs : ArrayList<ProgramEntity>,
                   val workouts : ArrayList<WorkoutEntity>,
                   val workoutsMap : MutableMap<Long, WorkoutEntity>,
                   var exercisesMap : MutableMap<Long, ExerciseEntity>,
                   var exercisesList : ArrayList<ExerciseEntity>,
                   val templateExercises : MutableMap<Long, ExerciseTemplateEntity>,
                   val performedExercises : MutableMap<Long, ExercisePerformedEntity>)