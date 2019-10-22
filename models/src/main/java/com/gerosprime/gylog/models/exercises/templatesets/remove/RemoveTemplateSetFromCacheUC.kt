package com.gerosprime.gylog.models.exercises.templatesets.remove

import io.reactivex.Single

interface RemoveTemplateSetFromCacheUC {
    fun remove(workoutIndex : Int, exerciseIndex : Int,
               templateSetIndex : Int
    ) : Single<RemoveTemplateFromCacheResult>
}