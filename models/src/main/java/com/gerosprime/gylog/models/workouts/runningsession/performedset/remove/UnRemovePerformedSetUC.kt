package com.gerosprime.gylog.models.workouts.runningsession.performedset.remove

import io.reactivex.Single

interface UnRemovePerformedSetUC {
    fun unflag(exercisePerformedIndex : Int,
               setIndex : Int) : Single<UnflagRemovePerformedSetResult>
}