package com.gerosprime.gylog.models.exercises

import io.reactivex.Maybe
import io.reactivex.Single

interface ExercisesCacheLoader {
    fun loadExercises() : Single<LoadedExercisesResult>

    fun loadExercise(recordId : Long?) : Maybe<LoadedSingleExerciseResult>
}