package com.gerosprime.gylog.models.exercises.templatesets.single

import com.gerosprime.gylog.models.states.EditProgramEntityCache
import io.reactivex.Single

class DefaultTemplateSetEditCacheLoader(private val editCache : EditProgramEntityCache)
    : TemplateSetEditCacheLoader {
    override fun load(
        workoutIndex: Int,
        exerciseIndex: Int,
        templateSetIndex: Int
    ): Single<TemplateSetEditLoadResult> {
        return Single.fromCallable {

            val templateSetEntity = editCache.editTemplateSets[templateSetIndex]

            TemplateSetEditLoadResult(workoutIndex, exerciseIndex, templateSetIndex,
                templateSetEntity)
        }
    }
}