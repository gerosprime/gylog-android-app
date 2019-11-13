package com.gerosprime.gylog.models.workouts.runningsession.discard

import io.reactivex.Single

interface RunningWorkoutSessionDiscardUC {
    fun discard() : Single<RunningWorkoutSessionDiscardResult>
}