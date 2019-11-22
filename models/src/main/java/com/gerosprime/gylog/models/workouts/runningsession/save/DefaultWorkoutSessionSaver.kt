package com.gerosprime.gylog.models.workouts.runningsession.save

import com.gerosprime.gylog.models.database.GylogEntityDatabase
import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.states.ModelsCache
import com.gerosprime.gylog.models.workouts.runningsession.WorkoutSessionEntity
import io.reactivex.Single

class DefaultWorkoutSessionSaver(private val modelsCache: ModelsCache,
                                 private val cacheBuilder: ModelCacheBuilder,
                                 private val database : GylogEntityDatabase
) : WorkoutSessionSaver {
    override fun save(session: WorkoutSessionEntity): Single<WorkoutSessionSaveResult> =
        cacheBuilder.build().andThen(Single.fromCallable {

            database.workoutSessionEntityDao().saveWholeSession(session)

            modelsCache.workoutsMap[session.workoutId]!!.lastWorkoutSessionId = session.recordId
            database.workoutEntityDao().saveWorkouts(listOf(modelsCache.workoutsMap[session.workoutId]!!))

            WorkoutSessionSaveResult(session)
        })

}