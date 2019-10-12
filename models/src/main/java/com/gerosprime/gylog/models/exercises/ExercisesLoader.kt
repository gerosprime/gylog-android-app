package com.gerosprime.gylog.models.exercises

import io.reactivex.Single

interface ExercisesLoader {
    fun loadExercises() : Single<LoadedExercisesResult>
}