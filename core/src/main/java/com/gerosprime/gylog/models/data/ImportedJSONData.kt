package com.gerosprime.gylog.models.data

import com.gerosprime.gylog.models.body.fat.BodyFatEntity
import com.gerosprime.gylog.models.body.weight.BodyWeightEntity
import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.models.exercises.performed.ExercisePerformedEntity
import com.gerosprime.gylog.models.exercises.performedsets.PerformedSetEntity
import com.gerosprime.gylog.models.exercises.templates.ExerciseTemplateEntity
import com.gerosprime.gylog.models.exercises.templatesets.TemplateSetEntity
import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.models.workouts.WorkoutEntity
import com.gerosprime.gylog.models.workouts.runningsession.WorkoutSessionEntity

class ImportedJSONData (val exercises : List<ExerciseEntity>,
                        val performedExercises : List<ExercisePerformedEntity>,
                        val templateExercises : List<ExerciseTemplateEntity>,
                        val performedSets : List<PerformedSetEntity>,
                        val programs : List<ProgramEntity>,
                        val templateSets : List<TemplateSetEntity>,
                        val workouts : List<WorkoutEntity>,
                        val workoutsSessions : List<WorkoutSessionEntity>,
                        val workoutBodyWeightEntities : List<BodyWeightEntity>,
                        val bodyFatEntities : List<BodyFatEntity>)