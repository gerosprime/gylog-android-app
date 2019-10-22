package com.gerosprime.gylog.models.exercises.templatesets

import io.reactivex.Single

interface EditTemplateSetsCacheLoader {
    fun load(workoutIndex : Int, workoutExercisesIndex : Int)
            : Single<LoadTemplateSetsToCacheResult>
}