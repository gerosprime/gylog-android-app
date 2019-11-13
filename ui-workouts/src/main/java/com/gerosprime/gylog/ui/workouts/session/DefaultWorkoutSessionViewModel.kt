package com.gerosprime.gylog.ui.workouts.session

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.FetchState
import com.gerosprime.gylog.base.components.viewmodel.BaseViewModel
import com.gerosprime.gylog.base.utils.TimeFormatUtil
import com.gerosprime.gylog.models.timer.CountDownEntity
import com.gerosprime.gylog.models.timer.CountDownTimer
import com.gerosprime.gylog.models.workouts.runningsession.create.WorkoutSessionCreationResult
import com.gerosprime.gylog.models.workouts.runningsession.create.WorkoutSessionCreator
import com.gerosprime.gylog.models.workouts.runningsession.discard.RunningWorkoutSessionDiscardResult
import com.gerosprime.gylog.models.workouts.runningsession.discard.RunningWorkoutSessionDiscardUC
import com.gerosprime.gylog.models.workouts.runningsession.finalizer.FinalizedRunningSessionResult
import com.gerosprime.gylog.models.workouts.runningsession.finalizer.RunningWorkoutSessionFinalizer
import com.gerosprime.gylog.models.workouts.runningsession.load.RunningWorkoutSessionLoader
import com.gerosprime.gylog.models.workouts.runningsession.load.WorkoutSessionCacheLoadResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.add.AddPerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.add.AddPerformedSetUC
import com.gerosprime.gylog.models.workouts.runningsession.performedset.edit.EditPerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.edit.EditPerformedSetUC
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.RemovePerformedSetUC
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.RemoveWorkoutSessionSetResult
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.UnRemovePerformedSetUC
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.UnflagRemovePerformedSetResult
import com.gerosprime.gylog.models.workouts.runningsession.save.WorkoutSessionSaveResult
import com.gerosprime.gylog.models.workouts.runningsession.save.WorkoutSessionSaver
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer


class DefaultWorkoutSessionViewModel(

    override val fetchStateMLD: MutableLiveData<FetchState>,
    override val workoutSessionLoadMLD: MutableLiveData<WorkoutSessionCacheLoadResult>,
    override val workoutSessionCreateMLD: MutableLiveData<WorkoutSessionCreationResult>,
    override val addSetMutableLiveData: MutableLiveData<AddPerformedSetResult>,
    override val removeSetMutableLiveData: MutableLiveData<RemoveWorkoutSessionSetResult>,
    override val unFlagRemoveSetMutableLiveData: MutableLiveData<UnflagRemovePerformedSetResult>,
    override val completeSetMutableLiveData: MutableLiveData<EditPerformedSetResult>,
    override val sessionTimerMLD: MutableLiveData<String>,
    override val restTimerMLD: MutableLiveData<String>,
    override val finalizedSessionMLD: MutableLiveData<FinalizedRunningSessionResult>,
    override val savedSessionMLD: MutableLiveData<WorkoutSessionSaveResult>,
    override val discardSessionMLD: MutableLiveData<RunningWorkoutSessionDiscardResult>,

    private val createWorkoutSessionUC : WorkoutSessionCreator,
    private val runningWorkoutSessionLoader: RunningWorkoutSessionLoader,
    private val sessionFinalizer: RunningWorkoutSessionFinalizer,
    private val sessionSaver : WorkoutSessionSaver,
    private val sessionDiscardUC : RunningWorkoutSessionDiscardUC,

    private val addPerformedSetUC: AddPerformedSetUC,
    private val removePerformedSetUC: RemovePerformedSetUC,
    private val unRemovePerformedSetUC: UnRemovePerformedSetUC,
    private val editPerformedSetUC: EditPerformedSetUC,

    private val uiScheduler: Scheduler?,
    private val backgroundScheduler: Scheduler?


) : BaseViewModel(), WorkoutSessionViewModel {


    private val compositeDisposable = CompositeDisposable()

    private var disposableTimer : Disposable? = null

    private val countDownTimer = CountDownTimer()
    private var countDownEntity : CountDownEntity? = null

    override fun createWorkoutSession(workoutRecordId: Long) {

        var creator = createWorkoutSessionUC.create(workoutRecordId)

        if (uiScheduler != null)
            creator = creator.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            creator = creator.subscribeOn(backgroundScheduler)

        compositeDisposable.add(creator.subscribe(
            Consumer {
                workoutSessionCreateMLD.value = it
            }))

    }

    override fun addSet(exercisePerformedIndex: Int) {
        var adder = addPerformedSetUC.add(exercisePerformedIndex)

        if (uiScheduler != null)
            adder = adder.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            adder = adder.subscribeOn(backgroundScheduler)

        compositeDisposable.add(adder.subscribe(
            Consumer {
                addSetMutableLiveData.value = it
            }))
    }

    override fun removeSet(exercisePerformedIndex: Int, setIndex: Int) {
        var remover = removePerformedSetUC.remove(exercisePerformedIndex, setIndex)

        if (uiScheduler != null)
            remover = remover.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            remover = remover.subscribeOn(backgroundScheduler)

        compositeDisposable.add(remover.subscribe(
            Consumer {
                removeSetMutableLiveData.value = it
            }))
    }

    override fun unRemoveTemplateSet(exercisePerformedIndex: Int, setIndex: Int) {
        var unRemover = unRemovePerformedSetUC.unflag(exercisePerformedIndex, setIndex)

        if (uiScheduler != null)
            unRemover = unRemover.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            unRemover = unRemover.subscribeOn(backgroundScheduler)

        compositeDisposable.add(unRemover.subscribe(
            Consumer {
                unFlagRemoveSetMutableLiveData.value = it
            }))
    }

    override fun editSet(
        exercisePerformedIndex: Int,
        setIndex: Int,
        reps: Int?,
        weight: Float?) {

        var edit = editPerformedSetUC.edit(exercisePerformedIndex, setIndex, reps, weight)

        if (uiScheduler != null)
            edit = edit.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            edit = edit.subscribeOn(backgroundScheduler)

        compositeDisposable.add(edit.subscribe(
            Consumer {
                completeSetMutableLiveData.value = it
            }))
    }

    override fun resumeWorkoutSession(workoutRecordId: Long) {

        var loader = runningWorkoutSessionLoader.load()

        if (uiScheduler != null)
            loader = loader.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            loader = loader.subscribeOn(backgroundScheduler)

        compositeDisposable.add(loader.subscribe(
            Consumer {
                workoutSessionLoadMLD.value = it
            }))

    }

    override fun finishWorkoutSession() {
        var finalizer = sessionFinalizer.finalizeSession()

        if (uiScheduler != null)
            finalizer = finalizer.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            finalizer = finalizer.subscribeOn(backgroundScheduler)

        compositeDisposable.add(finalizer.subscribe(Consumer {
            finalizedSessionMLD.value = it
        }))

    }

    override fun discardWorkoutSession() {
        var discard = sessionDiscardUC.discard()

        if (uiScheduler != null)
            discard = discard.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            discard = discard.subscribeOn(backgroundScheduler)

        compositeDisposable.add(discard.subscribe(Consumer {
            discardSessionMLD.value = it
        }))
    }

    override fun isResting(): Boolean = countDownEntity != null && countDownEntity!!.seconds > 0

    override fun restSet(duration : Int) {

        if (disposableTimer != null && !disposableTimer!!.isDisposed)
            disposableTimer!!.dispose()

        countDownEntity = CountDownEntity(duration)
        var timer = countDownTimer.create(countDownEntity).map { it.seconds.toLong() }

        if (uiScheduler != null)
            timer = timer.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            timer = timer.subscribeOn(backgroundScheduler)

        disposableTimer = timer.subscribe {
            if (it > 0) {
                restTimerMLD.value = TimeFormatUtil.secondsToString(it)
            } else {
                restTimerMLD.value = null
                cancelRest()
            }
        }
    }

    override fun cancelRest() {
        if (disposableTimer != null && !disposableTimer!!.isDisposed)
            disposableTimer!!.dispose()
        disposableTimer = null
    }

}