package com.gerosprime.gylog.models.body.weight

import java.util.*

class AllBodyWeightsCacheLoadResult(
    val bodyWeightMap: MutableMap<Long, BodyWeightEntity>,
    val bodyWeightArray : ArrayList<BodyWeightEntity>)
