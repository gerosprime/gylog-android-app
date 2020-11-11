package com.gerosprime.gylog.models.workouts.edit.add

import com.gerosprime.gylog.models.workouts.WorkoutEntity
import io.reactivex.Single

interface WorkoutAddToCacheUseCase {
    fun add(workoutEntity: WorkoutEntity) : Single<WorkoutAddToCacheResult>
}