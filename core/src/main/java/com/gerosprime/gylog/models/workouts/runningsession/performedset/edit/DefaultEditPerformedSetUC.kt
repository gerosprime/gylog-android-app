package com.gerosprime.gylog.models.workouts.runningsession.performedset.edit

import com.gerosprime.gylog.models.states.RunningWorkoutSessionCache
import io.reactivex.Single
import java.util.*

class DefaultEditPerformedSetUC(private val sessionCache: RunningWorkoutSessionCache)
    : EditPerformedSetUC {

    override fun edit(
        exercisePerformedIndex: Int,
        setIndex: Int,
        reps: Int?,
        weight: Float?,
        performedDate : Date?
    ): Single<EditPerformedSetResult> = Single.fromCallable {

        val exercise = sessionCache.prePerformedExercises!![exercisePerformedIndex]
        val performedSet = exercise.performedSets[setIndex]
        performedSet.reps = reps
        performedSet.weight = weight
        performedSet.datePerformed = performedDate

        val exerciseDate = exercise.performedDate
        if (exerciseDate == null || exerciseDate.before(performedDate)) {
            exercise.performedDate = performedDate
        }

        EditPerformedSetResult(exercisePerformedIndex, setIndex, performedSet,
            reps, weight)

    }

    override fun clear(
        exercisePerformedIndex: Int,
        setIndex: Int
    ): Single<ClearPerformedSetResult> = Single.fromCallable {
        val performedSet = sessionCache.prePerformedExercises!![exercisePerformedIndex]
            .performedSets[setIndex]
        performedSet.reps = null
        performedSet.weight = null
        performedSet.datePerformed = null
        ClearPerformedSetResult(exercisePerformedIndex, setIndex, performedSet)

    }
}