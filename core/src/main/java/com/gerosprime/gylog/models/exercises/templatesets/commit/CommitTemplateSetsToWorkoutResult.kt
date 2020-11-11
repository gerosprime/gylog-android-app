package com.gerosprime.gylog.models.exercises.templatesets.commit

import com.gerosprime.gylog.models.exercises.templatesets.TemplateSetEntity

class CommitTemplateSetsToWorkoutResult (val workoutIndex: Int,
                                         val exerciseIndex: Int,
                                         val templates: List<TemplateSetEntity>,
                                         val deleteTemplates : List<TemplateSetEntity>)