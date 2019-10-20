package com.gerosprime.gylog.models.workouts

import com.gerosprime.gylog.models.states.EditProgramEntityState
import io.reactivex.Single

class DefaultWorkoutAddToCacheUC
    (private val editProgramEntityState: EditProgramEntityState)
    : WorkoutAddToCacheUseCase {

    override fun add(workoutEntity: WorkoutEntity): Single<WorkoutAddToCacheResult> {
        return Single.fromCallable {

            val workouts = editProgramEntityState.workouts
            workouts!!.add(workoutEntity)

            val index = workouts.indexOf(workoutEntity)
            workoutEntity.day = index + 1
            WorkoutAddToCacheResult(workoutEntity, index)

        }
    }
}