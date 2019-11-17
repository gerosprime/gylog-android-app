package com.gerosprime.gylog.models.workouts.runningsession.finalizer

import com.gerosprime.gylog.models.states.RunningWorkoutSessionCache
import io.reactivex.Single
import java.util.*

class DefaultRunningWorkoutSessionFinalizer
    (private val sessionCache: RunningWorkoutSessionCache) : RunningWorkoutSessionFinalizer {

    override fun finalizeSession(): Single<FinalizedRunningSessionResult>
            = Single.fromCallable {

        val session = sessionCache.workoutSessionEntity
        val performedExercises = sessionCache.prePerformedExercises

        session!!.exercisesPerformed = performedExercises!!

        val calStart = Calendar.getInstance()
        calStart.timeInMillis = session.startDate!!.time

        val calEnd = Calendar.getInstance()

        val durationSeconds = (calEnd.timeInMillis - calStart.timeInMillis) / 1000

        session.durationSeconds = durationSeconds

        FinalizedRunningSessionResult(session)
    }
}