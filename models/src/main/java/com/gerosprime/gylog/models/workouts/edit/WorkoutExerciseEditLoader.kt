package com.gerosprime.gylog.models.workouts.edit

import io.reactivex.Single


interface WorkoutExerciseEditLoader {
    fun loadWorkoutExercises(workoutIndex : Int) :
            Single<WorkoutExerciseEditLoadResult>
}