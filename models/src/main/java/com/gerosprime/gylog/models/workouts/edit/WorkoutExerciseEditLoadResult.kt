package com.gerosprime.gylog.models.workouts.edit

import com.gerosprime.gylog.models.exercises.ExerciseEntity

class WorkoutExerciseEditLoadResult(val workoutExercises: MutableMap<Long, ExerciseEntity>,
                                    val exercises : List<ExerciseEntity>) {

}