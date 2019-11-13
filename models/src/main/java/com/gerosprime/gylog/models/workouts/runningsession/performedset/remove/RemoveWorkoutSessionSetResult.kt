package com.gerosprime.gylog.models.workouts.runningsession.performedset.remove

import com.gerosprime.gylog.models.exercises.performedsets.PerformedSetEntity

class RemoveWorkoutSessionSetResult(
    val exercisePerformedIndex: Int,
    val setIndex: Int,
    val performedSetEntity: PerformedSetEntity,
    val flagRemoved: Boolean
)