package com.gerosprime.gylog.models.workouts.runningsession.save

import com.gerosprime.gylog.models.states.ModelsCache
import com.gerosprime.gylog.models.workouts.runningsession.WorkoutSessionEntity
import io.reactivex.Single

class DefaultWorkoutSessionSaver(private val modelsCache: ModelsCache) : WorkoutSessionSaver {
    override fun save(session: WorkoutSessionEntity): Single<WorkoutSessionSaveResult> =
        Single.fromCallable { WorkoutSessionSaveResult(session) }
}