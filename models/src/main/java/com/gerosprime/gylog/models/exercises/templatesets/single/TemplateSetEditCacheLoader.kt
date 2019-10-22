package com.gerosprime.gylog.models.exercises.templatesets.single

import io.reactivex.Single

interface TemplateSetEditCacheLoader {
    fun load(workoutIndex : Int, exerciseIndex : Int, templateSetIndex : Int) : Single<TemplateSetEditLoadResult>
}