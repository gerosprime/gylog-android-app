package com.gerosprime.gylog.ui.workouts.exercises

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.base.components.viewmodel.BaseViewModel
import com.gerosprime.gylog.models.exercises.ExerciseEntity
import com.gerosprime.gylog.models.workouts.edit.load.WorkoutExerciseEditLoadResult
import com.gerosprime.gylog.models.workouts.edit.load.WorkoutExerciseEditLoader
import com.gerosprime.gylog.models.workouts.edit.commit.WorkoutExerciseSetCacheResult
import com.gerosprime.gylog.models.workouts.edit.commit.WorkoutSetExerciseCacheUC
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

class DefaultWorkoutExerciseEditViewModel(override var exercisesMutableLiveData: MutableLiveData<WorkoutExerciseEditLoadResult>,
                                          override var fetchStateMutableLiveData: MutableLiveData<FetchState>,
                                          override var exerciseCacheMutableLiveData: MutableLiveData<WorkoutExerciseSetCacheResult>,

                                          private var editLoader: WorkoutExerciseEditLoader,
                                          private var editExerciseUC : WorkoutSetExerciseCacheUC,

                                          private val uiScheduler : Scheduler?,
                                          private val backgroundScheduler: Scheduler)

    : BaseViewModel(), WorkoutExerciseEditViewModel {

    private val compositeDisposable = CompositeDisposable()

    override fun loadExercises(workoutIndex: Int) {

        var editLoader = this.editLoader.loadWorkoutExercises(workoutIndex)

        if (uiScheduler != null)
            editLoader = editLoader.observeOn(uiScheduler)

        if (uiScheduler != null)
            editLoader = editLoader.subscribeOn(backgroundScheduler)

        fetchStateMutableLiveData.value = FetchState.FETCHING
        compositeDisposable.add(editLoader
            .subscribe(Consumer {
                exercisesMutableLiveData.value =  it
                fetchStateMutableLiveData.value = FetchState.LOADED
            }))

    }

    override fun addExercisesIntoWorkout(
        workoutIndex: Int,
        selectedExercises: List<ExerciseEntity>
    ) {
        var editor = editExerciseUC.setNewExercises(workoutIndex, selectedExercises)

        if (uiScheduler != null)
            editor = editor.observeOn(uiScheduler)

        if (uiScheduler != null)
            editor = editor.subscribeOn(backgroundScheduler)

        compositeDisposable.add(editor.subscribe(Consumer {
            exerciseCacheMutableLiveData.value = it
        }))

    }
}