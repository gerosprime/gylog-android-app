package com.gerosprime.gylog.models.workouts.runningsession.performedset.add

import com.gerosprime.gylog.models.exercises.performedsets.PerformedSetEntity
import com.gerosprime.gylog.models.states.RunningWorkoutSessionCache
import io.reactivex.Single


class DefaultAddPerformedSetUC(private val cache: RunningWorkoutSessionCache)
    : AddPerformedSetUC {

    override fun add(exercisePerformedIndex: Int): Single<AddPerformedSetResult> =
        Single.fromCallable {
            val performed = PerformedSetEntity(recordId = null,
                reps = null,
                weight = null,
                counterWeight = null,
                durationSeconds = 0,
                restTimeSeconds = 30,
                upToFailure = false,
                templateSetId = null,
                initialReps = null,
                initialWeight = null,
                previousPerformedSetId = null,
                previousReps = null,
                previousWeight = null,
                datePerformed = null)

            // val size = cache.prePerformedExercises.size
            cache.prePerformedExercises!![exercisePerformedIndex]
                .performedSets.add(performed)

            AddPerformedSetResult(performed, exercisePerformedIndex)
        }
}