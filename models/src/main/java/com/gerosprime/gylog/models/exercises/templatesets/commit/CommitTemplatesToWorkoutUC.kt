package com.gerosprime.gylog.models.exercises.templatesets.commit

import com.gerosprime.gylog.models.exercises.templatesets.TemplateSetEntity
import io.reactivex.Single


interface CommitTemplatesToWorkoutUC {
    fun commit(workoutIndex : Int, exerciseIndex : Int): Single<CommitTemplateSetsToWorkoutResult>
}