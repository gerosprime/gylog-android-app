package com.gerosprime.gylog.models.workouts.edit.add

import com.gerosprime.gylog.models.states.EditProgramEntityCache
import com.gerosprime.gylog.models.workouts.WorkoutEntity
import io.reactivex.Single

class DefaultWorkoutAddToCacheUC
    (private val editProgramEntityCache: EditProgramEntityCache)
    : WorkoutAddToCacheUseCase {

    override fun add(workoutEntity: WorkoutEntity): Single<WorkoutAddToCacheResult> {
        return Single.fromCallable {

            val workouts = editProgramEntityCache.editWorkouts
            workouts.add(workoutEntity)

            val index = workouts.indexOf(workoutEntity)
            workoutEntity.day = index + 1
            WorkoutAddToCacheResult(
                workoutEntity,
                index
            )

        }
    }
}