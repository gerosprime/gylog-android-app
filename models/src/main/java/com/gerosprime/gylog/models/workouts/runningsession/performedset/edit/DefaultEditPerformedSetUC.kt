package com.gerosprime.gylog.models.workouts.runningsession.performedset.edit

import com.gerosprime.gylog.models.states.RunningWorkoutSessionCache
import io.reactivex.Single

class DefaultEditPerformedSetUC(private val sessionCache: RunningWorkoutSessionCache)
    : EditPerformedSetUC {

    override fun edit(
        exercisePerformedIndex: Int,
        setIndex: Int,
        reps: Int?,
        weight: Float?
    ): Single<EditPerformedSetResult> = Single.fromCallable {

        val performedSet = sessionCache.prePerformedExercises!![exercisePerformedIndex]
            .performedSets[setIndex]
        performedSet.reps = reps
        performedSet.weight = weight

        EditPerformedSetResult(exercisePerformedIndex, setIndex, performedSet,
            reps, weight)

    }
}