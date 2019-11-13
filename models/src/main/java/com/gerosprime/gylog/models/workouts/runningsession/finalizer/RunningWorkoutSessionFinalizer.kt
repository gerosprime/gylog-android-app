package com.gerosprime.gylog.models.workouts.runningsession.finalizer

import io.reactivex.Single

interface RunningWorkoutSessionFinalizer {
    fun finalizeSession() : Single<FinalizedRunningSessionResult>
}