package com.gerosprime.gylog.ui.exercises.templatesets

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.base.components.viewmodel.BaseViewModel
import com.gerosprime.gylog.models.exercises.templatesets.EditTemplateSetsCacheLoader
import com.gerosprime.gylog.models.exercises.templatesets.LoadTemplateSetsToCacheResult
import com.gerosprime.gylog.models.exercises.templatesets.TemplateSetEntity
import com.gerosprime.gylog.models.exercises.templatesets.add.CreateTemplateSetToCacheResult
import com.gerosprime.gylog.models.exercises.templatesets.add.CreateTemplateSetToCacheUC
import com.gerosprime.gylog.models.exercises.templatesets.commit.CommitTemplateSetsToWorkoutResult
import com.gerosprime.gylog.models.exercises.templatesets.commit.CommitTemplatesToWorkoutUC
import com.gerosprime.gylog.models.exercises.templatesets.remove.RemoveTemplateFromCacheResult
import com.gerosprime.gylog.models.exercises.templatesets.remove.RemoveTemplateSetFromCacheUC
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

class DefaultEditTemplateSetsViewModel(
    override val loadTemplateSetsMutableLiveData: MutableLiveData<LoadTemplateSetsToCacheResult>,
    override val fetchStateMutableLiveData: MutableLiveData<FetchState>,
    override val removeTemplateResultMutableLiveData: MutableLiveData<RemoveTemplateFromCacheResult>,
    override val addTemplateResultMutableLiveData: MutableLiveData<CreateTemplateSetToCacheResult>,
    override val commitTemplateResultMutableLiveData: MutableLiveData<CommitTemplateSetsToWorkoutResult>,

    private val editTemplateSetsCacheLoader: EditTemplateSetsCacheLoader,
    private val addTemplateSetsCache : CreateTemplateSetToCacheUC,
    private val removeTemplateSetsCache : RemoveTemplateSetFromCacheUC,
    private val commitTemplateSetsCache: CommitTemplatesToWorkoutUC,


    private val backgroundScheduler : Scheduler? = null,
    private val uiScheduler: Scheduler? = null

) : BaseViewModel(), EditTemplateSetsViewModel {

    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun loadTemplateSets(workoutIndex : Int, workoutExercisesIndex : Int) {
        var loader = editTemplateSetsCacheLoader.load(workoutIndex, workoutExercisesIndex)

        if (uiScheduler != null)
            loader = loader.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            loader = loader.subscribeOn(backgroundScheduler)

        compositeDisposable.add(loader.subscribe(Consumer { loadTemplateSetsMutableLiveData.value = it }))
    }

    override fun addTemplate(workoutIndex : Int, workoutExercisesIndex : Int) {
        var added = addTemplateSetsCache.add(workoutIndex, workoutExercisesIndex)

        if (uiScheduler != null)
            added = added.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            added = added.subscribeOn(backgroundScheduler)

        compositeDisposable.add(added.subscribe(
            Consumer { addTemplateResultMutableLiveData.value = it }))
    }

    override fun removeTemplate(templatesetIndex: Int,
                                workoutIndex : Int, workoutExercisesIndex : Int) {
        var remove = removeTemplateSetsCache.remove(workoutIndex, workoutExercisesIndex,
            templatesetIndex)

        if (uiScheduler != null)
            remove = remove.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            remove = remove.subscribeOn(backgroundScheduler)

        compositeDisposable.add(remove
            .subscribe(Consumer { removeTemplateResultMutableLiveData.value = it }))

    }

    override fun commitTemplates(workoutIndex : Int, workoutExercisesIndex : Int) {
        var commit = commitTemplateSetsCache.commit(workoutIndex, workoutExercisesIndex)

        if (uiScheduler != null)
            commit = commit.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            commit = commit.subscribeOn(backgroundScheduler)

        compositeDisposable.add(commit.subscribe(
            Consumer {
                commitTemplateResultMutableLiveData.value = it
            }))

    }
}