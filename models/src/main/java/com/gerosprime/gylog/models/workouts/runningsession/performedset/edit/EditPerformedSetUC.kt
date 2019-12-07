package com.gerosprime.gylog.models.workouts.runningsession.performedset.edit

import io.reactivex.Single
import java.util.*

interface EditPerformedSetUC {
    fun edit(exercisePerformedIndex : Int,
             setIndex : Int,
             reps : Int?,
             weight : Float?,
             performedDate : Date?
    ) : Single<EditPerformedSetResult>

    fun clear(exercisePerformedIndex : Int,
              setIndex : Int) : Single<ClearPerformedSetResult>
}