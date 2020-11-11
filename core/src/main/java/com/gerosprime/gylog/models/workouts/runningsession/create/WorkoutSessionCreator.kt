package com.gerosprime.gylog.models.workouts.runningsession.create

import io.reactivex.Single

interface WorkoutSessionCreator {
    fun create(workoutEntityId : Long) : Single<WorkoutSessionCreationResult>
}