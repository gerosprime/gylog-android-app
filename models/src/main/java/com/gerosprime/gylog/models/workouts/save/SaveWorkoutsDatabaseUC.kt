package com.gerosprime.gylog.models.workouts.save

import com.gerosprime.gylog.models.workouts.WorkoutEntity
import io.reactivex.Single

interface SaveWorkoutsDatabaseUC {
    fun save(workouts : ArrayList<WorkoutEntity>) : Single<SaveWorkoutsDatabaseResult>
}