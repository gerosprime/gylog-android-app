package com.gerosprime.gylog.models.exercises.templatesets.remove

import com.gerosprime.gylog.models.exercises.templatesets.TemplateSetEntity

class RemoveTemplateFromCacheResult(val templateSetEntity: TemplateSetEntity,
                                    val templateRemovedIndex : Int,
                                    val workoutIndex: Int,
                                    val exerciseIndex: Int,
                                    val totalWeight : Float,
                                    val totalSets : Int,
                                    val totalRestDuration : Int)