package com.gerosprime.gylog.models.exercises.templatesets.add

import com.gerosprime.gylog.models.exercises.templatesets.TemplateSetEntity

class CreateTemplateSetToCacheResult(val templateSet : TemplateSetEntity,
                                     val insertIndex : Int,
                                     val workoutIndex : Int,
                                     val exerciseIndex : Int,
                                     val totalWeight : Float,
                                     val totalSets : Int,
                                     val totalRest : Int)