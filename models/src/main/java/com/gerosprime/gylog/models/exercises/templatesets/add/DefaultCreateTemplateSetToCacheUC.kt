package com.gerosprime.gylog.models.exercises.templatesets.add

import com.gerosprime.gylog.models.exercises.templatesets.TemplateSetEntity
import com.gerosprime.gylog.models.states.EditProgramEntityCache
import io.reactivex.Single


class DefaultCreateTemplateSetToCacheUC
    (private val editCache: EditProgramEntityCache) : CreateTemplateSetToCacheUC {


    override fun add(
        workoutIndex: Int,
        exerciseIndex: Int
    ): Single<CreateTemplateSetToCacheResult> {
        return Single.fromCallable {

            val templateSets = editCache.editTemplateSets

            val newTemplateSet =
                TemplateSetEntity(recordId = null)
            templateSets.add(templateSets.size, newTemplateSet)

            val insertIndex = templateSets.size - 1

            var totalWeight = 0f
            var totalRest = 0
            var totalSets = templateSets.size

            for (templateSet in templateSets) {
                totalWeight += templateSet.weight
                totalRest += templateSet.restTimeSeconds
            }

            CreateTemplateSetToCacheResult(newTemplateSet, insertIndex,
                workoutIndex, exerciseIndex, totalWeight, totalSets, totalRest)
        }
    }
}