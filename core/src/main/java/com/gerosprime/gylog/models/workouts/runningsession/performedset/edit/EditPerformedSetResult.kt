package com.gerosprime.gylog.models.workouts.runningsession.performedset.edit

import com.gerosprime.gylog.models.exercises.performedsets.PerformedSetEntity

class EditPerformedSetResult(val exercisePerformedIndex: Int,
                             val setIndex: Int,
                             val performedSet: PerformedSetEntity,
                             val reps: Int?,
                             val weight: Float?)