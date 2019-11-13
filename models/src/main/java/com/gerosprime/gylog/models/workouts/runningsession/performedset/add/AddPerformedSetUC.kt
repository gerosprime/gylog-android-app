package com.gerosprime.gylog.models.workouts.runningsession.performedset.add

import io.reactivex.Single

interface AddPerformedSetUC {
    fun add(exercisePerformedIndex : Int) : Single<AddPerformedSetResult>
}