package com.gerosprime.gylog.ui.workouts.session.info

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.base.components.viewmodel.BaseViewModel
import com.gerosprime.gylog.models.timer.StopWatch
import com.gerosprime.gylog.models.timer.StopWatchEntity
import com.gerosprime.gylog.models.workouts.runningsession.load.RunningWorkoutSessionLoader
import com.gerosprime.gylog.models.workouts.runningsession.load.WorkoutSessionInfoLoadResult
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import java.util.*

class DefaultSessionInfoViewModel
    (override val sessionInfoMLD: MutableLiveData<WorkoutSessionInfoLoadResult>,
     override val sessionInfoStopwatchMLD: MutableLiveData<StopWatchEntity>,
     private val runningWorkoutSessionLoader: RunningWorkoutSessionLoader,
     private val uiScheduler: Scheduler?, private val backgroundScheduler: Scheduler?,
     private var computationScheduler: Scheduler?)
    : BaseViewModel(), SessionInfoViewModel {

    private val compositeDisposable = CompositeDisposable()

    private var stopwatchDisposable: Disposable? = null

    private val stopWatch : StopWatch = StopWatch()
    private lateinit var sessionDuration : StopWatchEntity


    override fun loadInfo() {
        var loader = runningWorkoutSessionLoader.loadInfo()

        if (uiScheduler != null)
            loader = loader.observeOn(uiScheduler)

        if (backgroundScheduler != null)
            loader = loader.subscribeOn(backgroundScheduler)

        compositeDisposable.add(loader.subscribe(Consumer {
            sessionInfoMLD.value = it
        }))

    }

    override fun startSessionDurationStopwatch(started : Date) {
        stopwatchDisposable?.dispose()

        val calendarNow = Calendar.getInstance()
        val calendarSession = Calendar.getInstance()
        calendarSession.time = started

        sessionDuration = StopWatchEntity(
            ((calendarNow.timeInMillis - calendarSession.timeInMillis) / 1000).toInt())

        var stopwatch = stopWatch.create(sessionDuration)

        if (uiScheduler != null)
            stopwatch = stopwatch.observeOn(uiScheduler)

        if (computationScheduler != null)
            stopwatch = stopwatch.subscribeOn(computationScheduler)

        stopwatchDisposable = stopwatch.subscribe {
            sessionInfoStopwatchMLD.value = it
        }

    }

    override fun stopSessionDurationStopwatch() {
        stopwatchDisposable?.dispose()
    }
}