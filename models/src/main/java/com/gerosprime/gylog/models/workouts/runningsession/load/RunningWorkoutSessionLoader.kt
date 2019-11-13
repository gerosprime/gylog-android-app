package com.gerosprime.gylog.models.workouts.runningsession.load

import io.reactivex.Single


interface RunningWorkoutSessionLoader {
    fun load() : Single<WorkoutSessionCacheLoadResult>
}