package com.gerosprime.gylog.models.exercises

import com.gerosprime.gylog.models.muscle.MuscleEnum

class LoadedExercisesResult (val exercises: List<ExerciseEntity>,
                             val targetMuscles: List<MuscleEnum>)