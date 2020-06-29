package com.gerosprime.gylog.ui.exercises.detail

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.components.viewmodel.BaseViewModel
import com.gerosprime.gylog.models.exercises.ExercisesCacheLoader
import com.gerosprime.gylog.models.exercises.LoadedSingleExerciseResult
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class DefaultExerciseDetailViewModel(
    override val loadedExerciseResultLiveData: MutableLiveData<LoadedSingleExerciseResult>,
    private val exercisesCacheLoader: ExercisesCacheLoader,
    private val uiScheduler: Scheduler?,
    private val backgroundScheduler: Scheduler?) : BaseViewModel(), ExerciseDetailViewModel {

    val compositeDisposable = CompositeDisposable()

    override fun load(exerciseId: Long) {
        var loader = exercisesCacheLoader.loadExercise(exerciseId)

        if (uiScheduler != null)
            loader = loader.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            loader = loader.subscribeOn(backgroundScheduler)

        compositeDisposable.add(loader.subscribe {
            loadedExerciseResultLiveData.value = it
        })
    }
}