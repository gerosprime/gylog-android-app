package com.gerosprime.gylog.models.workouts

import com.gerosprime.gylog.models.states.AddProgramEntityState
import io.reactivex.Single

class DefaultWorkoutAddToCacheUC
    (private val addProgramEntityState: AddProgramEntityState)
    : WorkoutAddToCacheUseCase {

    override fun add(workoutEntity: WorkoutEntity): Single<WorkoutAddToCacheResult> {
        return Single.fromCallable {

            val workouts = addProgramEntityState.workouts
            workouts!!.add(workoutEntity)

            val index = workouts.indexOf(workoutEntity)
            workoutEntity.day = index + 1
            WorkoutAddToCacheResult(workoutEntity, index)

        }
    }
}