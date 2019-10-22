package com.gerosprime.gylog.models.workouts.edit.load

import io.reactivex.Single


interface WorkoutExerciseEditLoader {
    fun loadWorkoutExercises(workoutIndex : Int) :
            Single<WorkoutExerciseEditLoadResult>
}