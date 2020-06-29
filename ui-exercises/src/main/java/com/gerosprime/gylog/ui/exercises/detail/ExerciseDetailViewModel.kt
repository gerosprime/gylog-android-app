package com.gerosprime.gylog.ui.exercises.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.models.exercises.LoadedSingleExerciseResult

interface ExerciseDetailViewModel {
    val loadedExerciseResultLiveData : LiveData<LoadedSingleExerciseResult>
    fun load(exerciseId : Long)
}