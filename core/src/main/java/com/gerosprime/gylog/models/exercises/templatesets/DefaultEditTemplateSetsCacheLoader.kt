package com.gerosprime.gylog.models.exercises.templatesets

import com.gerosprime.gylog.models.states.EditProgramEntityCache
import io.reactivex.Single

class DefaultEditTemplateSetsCacheLoader(private val editCache: EditProgramEntityCache)
    : EditTemplateSetsCacheLoader {


    override fun load(
        workoutIndex: Int,
        workoutExercisesIndex: Int
    ): Single<LoadTemplateSetsToCacheResult> {
        return Single.fromCallable {

            editCache.editExercisesTemplate = editCache.editWorkouts[workoutIndex].exercises
            //
            val templateExercise = editCache.editExercisesTemplate[workoutExercisesIndex]
            val templates
                    = templateExercise.setTemplates

            val copyTemplates : ArrayList<TemplateSetEntity> = arrayListOf()

            var totalWeight = 0f
            var totalRestDuration = 0
            var totalSets = templates.size

            for (template in templates) {
                copyTemplates.add(template.deepCopy())

                // Compute total sets, duration, and weight
                if (template.weight != null) totalWeight += template.weight!!
                totalRestDuration += template.restTimeSeconds
            }

            editCache.editTemplateSets = copyTemplates

            editCache.deleteTemplateSets = arrayListOf()

            LoadTemplateSetsToCacheResult(workoutIndex, workoutExercisesIndex, templateExercise,
                copyTemplates, totalWeight, totalSets, totalRestDuration)
        }
    }
}