package com.gerosprime.gylog.ui.workouts.session.info

import androidx.lifecycle.MutableLiveData
import com.gerosprime.gylog.models.timer.StopWatchEntity
import com.gerosprime.gylog.models.workouts.runningsession.load.WorkoutSessionInfoLoadResult
import java.util.*

interface SessionInfoViewModel {
    val sessionInfoMLD : MutableLiveData<WorkoutSessionInfoLoadResult>
    val sessionInfoStopwatchMLD : MutableLiveData<StopWatchEntity>

    fun loadInfo()

    fun startSessionDurationStopwatch(started : Date)
    fun stopSessionDurationStopwatch()

}