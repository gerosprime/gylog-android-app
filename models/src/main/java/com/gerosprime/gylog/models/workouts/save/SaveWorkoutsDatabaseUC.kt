package com.gerosprime.gylog.models.workouts.save

import com.gerosprime.gylog.models.workouts.WorkoutEntity
import io.reactivex.Single

interface SaveWorkoutsDatabaseUC {
    fun save(programId : Long,
             workouts : ArrayList<WorkoutEntity>) : Single<SaveWorkoutsDatabaseResult>
}