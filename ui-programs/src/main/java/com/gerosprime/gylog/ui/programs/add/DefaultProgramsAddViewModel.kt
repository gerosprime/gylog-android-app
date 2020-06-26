package com.gerosprime.gylog.ui.programs.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.components.viewmodel.BaseViewModel
import com.gerosprime.gylog.models.programs.edit.commit.CommitEdittedProgramCacheUC
import com.gerosprime.gylog.models.programs.edit.load.EditProgramCacheSetterUseCase
import com.gerosprime.gylog.models.programs.edit.load.EditProgramSetToCacheResult
import com.gerosprime.gylog.models.programs.save.SaveProgramDatabaseResult
import com.gerosprime.gylog.models.programs.save.SaveProgramDatabaseUC
import com.gerosprime.gylog.models.states.EditCacheClearUC
import com.gerosprime.gylog.models.workouts.WorkoutEntity
import com.gerosprime.gylog.models.workouts.edit.add.WorkoutAddToCacheResult
import com.gerosprime.gylog.models.workouts.edit.add.WorkoutAddToCacheUseCase
import com.gerosprime.gylog.models.workouts.save.SaveWorkoutsDatabaseUC
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

class DefaultProgramsAddViewModel(
    private val workoutAddToCacheUseCase: WorkoutAddToCacheUseCase,
    private val editProgramCacheSetterUseCase: EditProgramCacheSetterUseCase,
    private val commitProgramCacheUseCase : CommitEdittedProgramCacheUC,

    private val saveProgramDatabaseUC: SaveProgramDatabaseUC,
    private val saveWorkoutsDatabaseUC: SaveWorkoutsDatabaseUC,

    private val clearCacheUC: EditCacheClearUC,

    private val uiScheduler: Scheduler? = null,
    private val backgroundScheduler: Scheduler? = null
) : BaseViewModel(), ProgramsAddViewModel {

    private val programSetMLD = MutableLiveData<EditProgramSetToCacheResult>()
    private val workoutAddToCacheMLD = MutableLiveData<WorkoutAddToCacheResult>()
    private val saveProgramMLD = MutableLiveData<SaveProgramDatabaseResult>()

    private val compositeDisposable = CompositeDisposable()

    override fun saveProgramToDB(name: String, description: String,
                                 imageUri : String) {

        var commitProgram =
            commitProgramCacheUseCase.commit(name, description, imageUri)
                .flatMap { commitResult ->
                    saveProgramDatabaseUC.save(commitResult.programEntity)
                }


        if (uiScheduler != null)
            commitProgram = commitProgram.observeOn(uiScheduler)

        if (uiScheduler != null)
            commitProgram = commitProgram.subscribeOn(backgroundScheduler)

        compositeDisposable.add(commitProgram.subscribe(Consumer { saveProgramMLD.value = it }))

    }

    override fun addWorkoutToCache(name: String?, description: String?) {
        var addToCacheUseCase = workoutAddToCacheUseCase
            .add(WorkoutEntity(name = name, description = description))

        if (uiScheduler != null)
            addToCacheUseCase = addToCacheUseCase.observeOn(uiScheduler)

        if (uiScheduler != null)
            addToCacheUseCase = addToCacheUseCase.subscribeOn(backgroundScheduler)

        compositeDisposable.add(addToCacheUseCase
            .subscribe(Consumer { workoutAddToCacheMLD.value =  it }))

    }

    override fun loadProgramForEdit(programRecordId : Long) {

        var programSetToCache = editProgramCacheSetterUseCase
            .editProgramSetToCache(programRecordId)

        if (uiScheduler != null)
            programSetToCache = programSetToCache.observeOn(uiScheduler)

        if (uiScheduler != null)
            programSetToCache = programSetToCache.subscribeOn(backgroundScheduler)

        compositeDisposable.add(programSetToCache
            .subscribe({ programSetMLD.value = it }, { it.printStackTrace() }))

    }

    override fun clearAll() {
        var clearCache = clearCacheUC.clear()

        if (uiScheduler != null)
            clearCache = clearCache.observeOn(uiScheduler)

        if (uiScheduler != null)
            clearCache = clearCache.subscribeOn(backgroundScheduler)

        compositeDisposable.add(clearCache.subscribe())

    }

    override val programSetToCacheResultMLD: LiveData<EditProgramSetToCacheResult>
        get() = programSetMLD

    override val workoutAddToCacheResultMLD: LiveData<WorkoutAddToCacheResult>
        get() = workoutAddToCacheMLD

    override val saveProgramResultMLD: LiveData<SaveProgramDatabaseResult>
        get() = saveProgramMLD

}