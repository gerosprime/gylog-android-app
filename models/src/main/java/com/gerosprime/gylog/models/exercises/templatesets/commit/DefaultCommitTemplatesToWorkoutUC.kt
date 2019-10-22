package com.gerosprime.gylog.models.exercises.templatesets.commit

import com.gerosprime.gylog.models.exercises.templatesets.TemplateSetEntity
import com.gerosprime.gylog.models.states.EditProgramEntityCache
import io.reactivex.Single


class DefaultCommitTemplatesToWorkoutUC(private val editCache: EditProgramEntityCache)
    : CommitTemplatesToWorkoutUC {

    override fun commit(
        workoutIndex: Int,
        exerciseIndex: Int
    ): Single<CommitTemplateSetsToWorkoutResult> {
        return Single.fromCallable {

            editCache.editExercisesTemplate[exerciseIndex].setTemplates =
                editCache.editTemplateSets

            CommitTemplateSetsToWorkoutResult(workoutIndex, exerciseIndex,
                editCache.editTemplateSets)
        }
    }
}