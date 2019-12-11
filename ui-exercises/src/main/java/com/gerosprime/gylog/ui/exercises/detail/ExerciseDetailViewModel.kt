package com.gerosprime.gylog.ui.exercises.detail

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.models.exercises.LoadedSingleExerciseResult

interface ExerciseDetailViewModel {
    val loadedExerciseResultMLD : MutableLiveData<LoadedSingleExerciseResult>
    fun load(exerciseId : Long)
}