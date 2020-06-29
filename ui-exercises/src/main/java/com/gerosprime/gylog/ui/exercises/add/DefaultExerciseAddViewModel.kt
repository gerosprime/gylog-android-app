package com.gerosprime.gylog.ui.exercises.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.base.components.viewmodel.BaseViewModel
import com.gerosprime.gylog.models.exercises.*
import com.gerosprime.gylog.models.muscle.MuscleEnum
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

class DefaultExerciseAddViewModel
    (
     private val uiScheduler: Scheduler?,
     private val backgroundScheduler: Scheduler?,
     private val exerciseSaver : ExerciseDatabaseSaver,
     private val exerciseLoader : ExercisesCacheLoader)

    : BaseViewModel(), ExerciseAddViewModel {


    private val fetchStateMLD = MutableLiveData<FetchState>()
    private val saveResultMLD = MutableLiveData<ExerciseDatabaseSaveResult>()
    private val loadResultMLD = MutableLiveData<LoadedSingleExerciseResult>()

    private val compositeDisposable  = CompositeDisposable()

    override fun saveExercise(
        recordId : Long?,
        name: String,
        description: String,
        direction: String,
        muscles: ArrayList<MuscleEnum>
    ) {


        val exercise = ExerciseEntity(recordId, name, description, direction)
        exercise.targetMuscles = muscles

        var saver = exerciseSaver.save(exercise)
        if (uiScheduler != null) {
            saver = saver.observeOn(uiScheduler)
        }

        if (backgroundScheduler != null) {
            saver = saver.subscribeOn(backgroundScheduler)
        }

        compositeDisposable.add(saver.subscribe(Consumer {
            saveResultMLD.value = it
        }))

    }

    override fun loadExercise(exerciseId: Long?) {
        var loader = exerciseLoader.loadExercise(exerciseId)
        if (uiScheduler != null) {
            loader = loader.observeOn(uiScheduler)
        }

        if (backgroundScheduler != null) {
            loader = loader.subscribeOn(backgroundScheduler)
        }

        compositeDisposable.add(loader.subscribe {
            loadResultMLD.value = it
        })
    }

    override val fetchStateLiveData: LiveData<FetchState>
        get() = fetchStateMLD
    override val loadResultLiveData: LiveData<LoadedSingleExerciseResult>
        get() = loadResultMLD
    override val saveResultLiveData: LiveData<ExerciseDatabaseSaveResult>
        get() = saveResultMLD

}