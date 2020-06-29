package com.gerosprime.gylog.ui.exercises.templatesets.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.base.components.viewmodel.BaseViewModel
import com.gerosprime.gylog.models.exercises.templatesets.single.TemplateSetEditCacheLoader
import com.gerosprime.gylog.models.exercises.templatesets.single.TemplateSetEditLoadResult
import com.gerosprime.gylog.models.exercises.templatesets.single.commit.TemplateSetCommitResult
import com.gerosprime.gylog.models.exercises.templatesets.single.commit.TemplateSetCommitUC
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer


class DefaultTemplateSetEditViewModel(

    private val templateSetloader : TemplateSetEditCacheLoader,
    private val templateSetCommitter : TemplateSetCommitUC,
    private val uiScheduler : Scheduler?,
    private val backgroundScheduler: Scheduler?) : BaseViewModel(), TemplateSetEditViewModel {

    private val fetchStateMLD = MutableLiveData<FetchState>()
    private val loadTemplateSetMLD = MutableLiveData<TemplateSetEditLoadResult>()
    private val commitMLD = MutableLiveData<TemplateSetCommitResult>()

    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun loadTemplate(workoutIndex: Int, exerciseIndex: Int, templateIndex: Int) {

        var loader = templateSetloader.load(workoutIndex, exerciseIndex, templateIndex)

        if (uiScheduler != null)
            loader = loader.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            loader = loader.subscribeOn(uiScheduler)

        compositeDisposable.add(loader.subscribe(Consumer { loadTemplateSetMLD.value = it }))

    }

    override fun commit(
        workoutIndex: Int,
        exerciseIndex: Int,
        templateIndex: Int,
        minReps: Int?,
        reps: Int?,
        weight: Float?,
        counterWeight: Float?,
        durationSeconds: Int,
        restTimeSeconds: Int
    ) {
        var committer = templateSetCommitter.commit(workoutIndex, exerciseIndex, templateIndex,
            minReps, reps, weight, counterWeight, durationSeconds, restTimeSeconds)

        if (uiScheduler != null)
            committer = committer.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            committer = committer.subscribeOn(uiScheduler)

        compositeDisposable.add(committer.subscribe(Consumer { commitMLD.value = it }))

    }

    override val fetchStateLiveData: LiveData<FetchState>
        get() = fetchStateMLD
    override val loadTemplateSetLiveData: LiveData<TemplateSetEditLoadResult>
        get() = loadTemplateSetMLD
    override val commitLiveData: LiveData<TemplateSetCommitResult>
        get() = commitMLD

}