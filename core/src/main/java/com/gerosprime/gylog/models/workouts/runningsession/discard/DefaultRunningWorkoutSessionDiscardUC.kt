package com.gerosprime.gylog.models.workouts.runningsession.discard

import com.gerosprime.gylog.models.states.RunningWorkoutSessionCache
import io.reactivex.Single

class DefaultRunningWorkoutSessionDiscardUC
    (private val runningSession : RunningWorkoutSessionCache)
    : RunningWorkoutSessionDiscardUC {
    override fun discard(): Single<RunningWorkoutSessionDiscardResult> =
        Single.fromCallable {
            runningSession.prePerformedExercises = null
            runningSession.workoutSessionEntity = null
        RunningWorkoutSessionDiscardResult()
    }
}