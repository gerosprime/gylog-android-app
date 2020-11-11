package com.gerosprime.gylog.models.workouts.runningsession.load

import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.states.ModelsCache
import com.gerosprime.gylog.models.states.RunningWorkoutSessionCache
import io.reactivex.Single


class DefaultRunningWorkoutSessionLoader
    (private val cacheBuilder: ModelCacheBuilder,
     private val modelsCache: ModelsCache,
     private val runningSessionCache: RunningWorkoutSessionCache)
    : RunningWorkoutSessionLoader {

    override fun load(): Single<WorkoutSessionCacheLoadResult> =
        Single.fromCallable {
            val workoutSession = runningSessionCache.workoutSessionEntity
            WorkoutSessionCacheLoadResult(workoutSession!!.workoutId, workoutSession,
                runningSessionCache.prePerformedExercises!!
            )
        }

    override fun loadInfo(): Single<WorkoutSessionInfoLoadResult>
            = cacheBuilder.build().andThen(Single.fromCallable {

        val session = runningSessionCache.workoutSessionEntity
        val workout = modelsCache.workoutsMap[session!!.workoutId]

        var totalSets = 0
        var setsPerformed = 0
        var totalWeight = 0f
        for (prePerformedExercise in runningSessionCache.prePerformedExercises!!) {

            for (performedSet in prePerformedExercise.performedSets) {
                if (performedSet.flagRemoved) continue
                if (performedSet.datePerformed != null) setsPerformed++
                if (performedSet.weight != null) totalWeight += performedSet.weight!!
            }

            totalSets += prePerformedExercise.performedSets.size
        }

        WorkoutSessionInfoLoadResult(workout!!, session,
            session.startDate!!, session.endDate, setsPerformed, totalSets, totalWeight)
    })
}