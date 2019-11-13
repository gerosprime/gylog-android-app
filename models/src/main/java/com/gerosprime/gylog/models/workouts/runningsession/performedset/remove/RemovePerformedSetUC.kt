package com.gerosprime.gylog.models.workouts.runningsession.performedset.remove

import io.reactivex.Single

interface RemovePerformedSetUC {
    fun remove(exercisePerformedIndex : Int,
               setIndex : Int) : Single<RemoveWorkoutSessionSetResult>
}