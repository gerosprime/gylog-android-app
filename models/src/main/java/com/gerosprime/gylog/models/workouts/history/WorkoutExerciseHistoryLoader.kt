package com.gerosprime.gylog.models.workouts.history

import io.reactivex.Single

interface WorkoutExerciseHistoryLoader {
    fun load(workoutId : Long, exerciseId : Long) : Single<WorkoutExerciseHistoryResult>
}