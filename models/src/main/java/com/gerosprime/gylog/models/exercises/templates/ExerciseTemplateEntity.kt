package com.gerosprime.gylog.models.exercises.templates

import androidx.room.PrimaryKey
import com.gerosprime.gylog.models.exercises.templatesets.TemplateSetEntity

data class ExerciseTemplateEntity(@PrimaryKey val recordId : Long? = null,
                                  val workoutId : Long? = null,
                                  val name : String,
                                  val exerciseId : Long,
                                  var setTemplates : ArrayList<TemplateSetEntity>) {
    fun deepCopy() : ExerciseTemplateEntity {

        val templateExerciseCopy =
            ExerciseTemplateEntity(
                recordId, workoutId,
                name, exerciseId, arrayListOf()
            )
        for (template in setTemplates) {
            templateExerciseCopy.setTemplates.add(template.deepCopy())
        }

        return templateExerciseCopy
    }
}