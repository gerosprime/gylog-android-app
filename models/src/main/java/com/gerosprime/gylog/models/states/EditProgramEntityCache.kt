package com.gerosprime.gylog.models.states

import com.gerosprime.gylog.models.exercises.templates.ExerciseTemplateEntity
import com.gerosprime.gylog.models.exercises.templatesets.TemplateSetEntity
import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.models.workouts.WorkoutEntity

class EditProgramEntityCache (var editProgram : ProgramEntity?, // Loaded when a program is being edited/added
                              // A Copy of arraylist holding references to workouts from program is created
                              var editWorkouts : ArrayList<WorkoutEntity>,
                              // A Copy of arraylist holding references to ex templates from a workout is created
                              var editExercisesTemplate : ArrayList<ExerciseTemplateEntity>,
                              var editExercisesTemplateMap : MutableMap<Long, ExerciseTemplateEntity>,
                              // A Copy of arraylist holding references to workouts from program is created
                              var editTemplateSets : ArrayList<TemplateSetEntity>)