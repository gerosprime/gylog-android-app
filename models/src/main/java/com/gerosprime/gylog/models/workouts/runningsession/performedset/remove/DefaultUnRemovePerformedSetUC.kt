package com.gerosprime.gylog.models.workouts.runningsession.performedset.remove

import com.gerosprime.gylog.models.states.RunningWorkoutSessionCache
import io.reactivex.Single


class DefaultUnRemovePerformedSetUC(private val sessionCache: RunningWorkoutSessionCache)
    : UnRemovePerformedSetUC {

    override fun unflag(
        exercisePerformedIndex: Int,
        setIndex: Int
    ): Single<UnflagRemovePerformedSetResult> = Single.fromCallable {

        sessionCache.prePerformedExercises!![exercisePerformedIndex]
            .performedSets[setIndex].flagRemoved = false
        UnflagRemovePerformedSetResult(exercisePerformedIndex, setIndex)

    }
}