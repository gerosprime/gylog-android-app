package com.gerosprime.gylog.models.exercises.templatesets.single.commit

import com.gerosprime.gylog.models.states.EditProgramEntityCache
import io.reactivex.Single

class DefaultTemplateSetCommitCacheUC(private val editState: EditProgramEntityCache)
    : TemplateSetCommitUC {

    override fun commit(
        workoutIndex: Int,
        exerciseIndex: Int,
        templateSetIndex: Int,
        minReps: Int,
        reps: Int,
        weight: Float,
        counterWeight: Float,
        durationSeconds: Int,
        restTimeSeconds: Int
    ): Single<TemplateSetCommitResult> = Single.fromCallable {


        val templateSetEntity = editState.editTemplateSets[templateSetIndex]

        templateSetEntity.minReps = minReps
        templateSetEntity.reps = reps
        templateSetEntity.weight = weight
        templateSetEntity.counterWeight = counterWeight
        templateSetEntity.durationSeconds = durationSeconds
        templateSetEntity.restTimeSeconds = restTimeSeconds

        TemplateSetCommitResult(workoutIndex, exerciseIndex, templateSetIndex, templateSetEntity)

    }
}