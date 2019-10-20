package com.gerosprime.gylog.models.exercises

import androidx.room.PrimaryKey

data class ExerciseTemplateEntity(@PrimaryKey val recordId : Long? = null,
                                  val workoutId : Long? = null,
                                  val name : String,
                                  val exerciseId : Long? = null,
                                  val setTemplates : List<TemplateSetEntity>)