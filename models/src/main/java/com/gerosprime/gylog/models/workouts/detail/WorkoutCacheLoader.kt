package com.gerosprime.gylog.models.workouts.detail

import io.reactivex.Single

interface WorkoutCacheLoader {
    fun load(workoutRecordId : Long) : Single<LoadWorkoutFromCacheResult>
}