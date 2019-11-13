package com.gerosprime.gylog.models.workouts.runningsession.load

import com.gerosprime.gylog.models.states.ModelsCache
import com.gerosprime.gylog.models.states.RunningWorkoutSessionCache
import io.reactivex.Single


class DefaultRunningWorkoutSessionLoader
    (private val modelsCache: ModelsCache,
     private val sessionCacheRunning: RunningWorkoutSessionCache)
    : RunningWorkoutSessionLoader {

    override fun load(): Single<WorkoutSessionCacheLoadResult> =


        Single.fromCallable {
            val workoutSession = sessionCacheRunning.workoutSessionEntity
            WorkoutSessionCacheLoadResult(workoutSession!!.workoutId, workoutSession,
                sessionCacheRunning.prePerformedExercises!!
            )
        }

}