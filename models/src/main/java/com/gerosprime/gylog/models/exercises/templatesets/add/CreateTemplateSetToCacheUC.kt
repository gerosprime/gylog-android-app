package com.gerosprime.gylog.models.exercises.templatesets.add

import io.reactivex.Single


interface CreateTemplateSetToCacheUC {
    fun add (workoutIndex : Int, exerciseIndex : Int) : Single<CreateTemplateSetToCacheResult>
}