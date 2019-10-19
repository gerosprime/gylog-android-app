package com.gerosprime.gylog.models.exercises

import androidx.room.PrimaryKey

data class ExerciseTemplateEntity(@PrimaryKey val recordId : Long,
                                  val workoutId : Long,
                                  val name : String,
                                  val setTemplates : List<TemplateSetEntity>)