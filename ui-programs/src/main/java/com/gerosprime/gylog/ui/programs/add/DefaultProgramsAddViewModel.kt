package com.gerosprime.gylog.ui.programs.add

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.components.viewmodel.BaseViewModel
import com.gerosprime.gylog.models.exercises.ExerciseTemplatesAddToCacheResult
import com.gerosprime.gylog.models.programs.EditProgramCacheSetterUseCase
import com.gerosprime.gylog.models.programs.EditProgramSetToCacheResult
import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.models.workouts.WorkoutAddToCacheResult
import com.gerosprime.gylog.models.workouts.WorkoutAddToCacheUseCase
import com.gerosprime.gylog.models.workouts.WorkoutEntity
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

class DefaultProgramsAddViewModel(
    override val programSetToCacheResultMLD: MutableLiveData<EditProgramSetToCacheResult>,
    override val workoutAddToCacheResultMLD: MutableLiveData<WorkoutAddToCacheResult>,

    private val workoutAddToCacheUseCase: WorkoutAddToCacheUseCase,
    private val editProgramCacheSetterUseCase: EditProgramCacheSetterUseCase,

    private val uiScheduler: Scheduler? = null,
    private val backgroundScheduler: Scheduler? = null
) : BaseViewModel(), ProgramsAddViewModel {

    private val compositeDisposable = CompositeDisposable()

    override fun saveProgramToDB(name: String, description: String) {
        TODO()
    }

    override fun addWorkoutToCache(name: String?, description: String?) {
        var addToCacheUseCase = workoutAddToCacheUseCase
            .add(WorkoutEntity(name = name, description = description, exercises = arrayListOf()))

        if (uiScheduler != null)
            addToCacheUseCase = addToCacheUseCase.observeOn(uiScheduler)

        if (uiScheduler != null)
            addToCacheUseCase = addToCacheUseCase.subscribeOn(backgroundScheduler)

        compositeDisposable.add(addToCacheUseCase
            .subscribe(Consumer { workoutAddToCacheResultMLD.value =  it }))

    }

    override fun loadNewProgram() {

        var programSetToCache = editProgramCacheSetterUseCase
            .editProgramSetToCache(ProgramEntity(workouts = arrayListOf()))

        if (uiScheduler != null)
            programSetToCache = programSetToCache.observeOn(uiScheduler)

        if (uiScheduler != null)
            programSetToCache = programSetToCache.subscribeOn(backgroundScheduler)

        compositeDisposable.add(programSetToCache
            .subscribe(Consumer { programSetToCacheResultMLD.value = it }))

    }

    override fun clearAll() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}