package com.gerosprime.gylog.models.workouts

import io.reactivex.Single

interface WorkoutAddToCacheUseCase {
    fun add(workoutEntity: WorkoutEntity) : Single<WorkoutAddToCacheResult>
}