package com.gerosprime.gylog.models.workouts.edit.load

import com.gerosprime.gylog.models.exercises.ExerciseEntity

class WorkoutExerciseEditLoadResult(val workoutExercisesMap: MutableMap<Long, ExerciseEntity>,
                                    val exercises : List<ExerciseEntity>) {

}