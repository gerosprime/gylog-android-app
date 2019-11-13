package com.gerosprime.gylog.models.workouts.runningsession.performedset.remove

import com.gerosprime.gylog.models.states.RunningWorkoutSessionCache
import io.reactivex.Single


class DefaultRemovePerformedSetUC(private val sessionCache: RunningWorkoutSessionCache)
    : RemovePerformedSetUC {


    override fun remove(
        exercisePerformedIndex: Int,
        setIndex: Int
    ): Single<RemoveWorkoutSessionSetResult> = Single.fromCallable {

        val exercisePerformed = sessionCache.prePerformedExercises!![exercisePerformedIndex]

        val performedSetEntity = exercisePerformed.performedSets[setIndex]

        if (performedSetEntity.isFreeSet)
            exercisePerformed.performedSets.remove(performedSetEntity)
        else
            performedSetEntity.flagRemoved = true

        RemoveWorkoutSessionSetResult(exercisePerformedIndex, setIndex,
            performedSetEntity, performedSetEntity.flagRemoved)

    }
}