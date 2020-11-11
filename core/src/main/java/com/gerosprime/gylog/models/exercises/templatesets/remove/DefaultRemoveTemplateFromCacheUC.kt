package com.gerosprime.gylog.models.exercises.templatesets.remove

import com.gerosprime.gylog.models.states.EditProgramEntityCache
import io.reactivex.Single


class DefaultRemoveTemplateFromCacheUC(private val editCache : EditProgramEntityCache)
    : RemoveTemplateSetFromCacheUC {

    override fun remove(
        workoutIndex: Int,
        exerciseIndex: Int,
        templateSetIndex : Int
    ): Single<RemoveTemplateFromCacheResult> {

        return Single.fromCallable {


            val removedTemplateSet =
            editCache.editTemplateSets.removeAt(templateSetIndex)
            editCache.deleteTemplateSets.add(removedTemplateSet)


            val templateSets = editCache.editTemplateSets

            var totalWeight = 0f
            var totalRest = 0
            var totalSets = templateSets.size

            for (templateSet in templateSets) {
                totalWeight += templateSet.weight!!
                totalRest += templateSet.restTimeSeconds
            }

            RemoveTemplateFromCacheResult(removedTemplateSet, templateSetIndex,
                workoutIndex, exerciseIndex, totalWeight, totalSets, totalRest)

        }

    }
}