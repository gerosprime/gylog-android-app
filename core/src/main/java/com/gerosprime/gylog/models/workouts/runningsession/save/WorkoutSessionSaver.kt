package com.gerosprime.gylog.models.workouts.runningsession.save

import com.gerosprime.gylog.models.workouts.runningsession.WorkoutSessionEntity
import io.reactivex.Single


interface WorkoutSessionSaver {
    fun save(session : WorkoutSessionEntity) : Single<WorkoutSessionSaveResult>
}