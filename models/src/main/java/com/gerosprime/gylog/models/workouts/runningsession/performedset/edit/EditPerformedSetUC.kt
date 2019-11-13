package com.gerosprime.gylog.models.workouts.runningsession.performedset.edit

import io.reactivex.Single

interface EditPerformedSetUC {
    fun edit(exercisePerformedIndex : Int,
             setIndex : Int,
             reps : Int?,
             weight : Float?) : Single<EditPerformedSetResult>
}