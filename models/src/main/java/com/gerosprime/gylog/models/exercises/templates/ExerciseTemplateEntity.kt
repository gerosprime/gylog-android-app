package com.gerosprime.gylog.models.exercises.templates

import androidx.room.*
import com.gerosprime.gylog.models.exercises.templatesets.TemplateSetEntity
import com.gerosprime.gylog.models.workouts.WorkoutEntity

@Entity(foreignKeys = [
    ForeignKey(entity = WorkoutEntity::class,
            parentColumns = ["recordId"], childColumns = ["workoutId"],
        onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)])
data class ExerciseTemplateEntity(@PrimaryKey var recordId : Long? = null,
                                  @ColumnInfo(index = true) var workoutId : Long? = null,
                                  val name : String,
                                  val exerciseId : Long) {

    @Ignore var setTemplates : ArrayList<TemplateSetEntity> = arrayListOf()

    fun deepCopy() : ExerciseTemplateEntity {

        val templateExerciseCopy =
            ExerciseTemplateEntity(
                recordId, workoutId,
                name, exerciseId
            )
        templateExerciseCopy.setTemplates = setTemplates
        for (template in setTemplates) {
            templateExerciseCopy.setTemplates.add(template.deepCopy())
        }

        return templateExerciseCopy
    }
}