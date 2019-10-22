package com.gerosprime.gylog.models.exercises.templatesets.single.commit

import io.reactivex.Single

interface TemplateSetCommitUC {
    fun commit(workoutIndex: Int,
               exerciseIndex: Int,
               templateSetIndex: Int,
               minReps : Int = 0,
               reps: Int = 0,
               weight: Float = 0f,
               counterWeight: Float = 0f,
               durationSeconds: Int = 0,
               restTimeSeconds : Int = 30
               ) : Single<TemplateSetCommitResult>
}