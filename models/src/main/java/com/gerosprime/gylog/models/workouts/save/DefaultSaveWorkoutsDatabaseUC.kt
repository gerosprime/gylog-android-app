package com.gerosprime.gylog.models.workouts.save

import com.gerosprime.gylog.models.states.ModelsCache
import com.gerosprime.gylog.models.workouts.WorkoutEntity
import io.reactivex.Single

class DefaultSaveWorkoutsDatabaseUC(private val modelsCache: ModelsCache) : SaveWorkoutsDatabaseUC {

    override fun save(workouts: ArrayList<WorkoutEntity>):
            Single<SaveWorkoutsDatabaseResult> = Single.fromCallable {

        for (workout in workouts) {
            var workoutRecordId = workout.recordId
            if (workoutRecordId == null) {
                workoutRecordId = (modelsCache.workouts.size + 1).toLong()
                workout.recordId = workoutRecordId
            }

            if (modelsCache.workoutsMap.containsKey(workoutRecordId)) {
                modelsCache.workouts.add(workout)
            }

            modelsCache.workoutsMap[workoutRecordId] = workout
        }

        SaveWorkoutsDatabaseResult(workouts = workouts)

    }
}