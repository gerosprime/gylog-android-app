package com.gerosprime.gylog.models.exercises.templatesets.single.commit

import io.reactivex.Single

interface TemplateSetCommitUC {
    fun commit(workoutIndex: Int,
               exerciseIndex: Int,
               templateSetIndex: Int,
               minReps : Int?,
               reps: Int?,
               weight: Float?,
               counterWeight: Float?,
               durationSeconds: Int,
               restTimeSeconds : Int
               ) : Single<TemplateSetCommitResult>
}