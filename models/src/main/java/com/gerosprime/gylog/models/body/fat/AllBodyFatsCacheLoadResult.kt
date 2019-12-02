package com.gerosprime.gylog.models.body.fat

import java.util.*

class AllBodyFatsCacheLoadResult(
    val bodyFatMap: MutableMap<Long, BodyFatEntity>,
    val bodyFatArray : ArrayList<BodyFatEntity>)
