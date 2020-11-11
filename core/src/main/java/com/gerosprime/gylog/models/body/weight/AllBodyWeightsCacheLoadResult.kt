package com.gerosprime.gylog.models.body.weight

class AllBodyWeightsCacheLoadResult(
    val bodyWeightMap: MutableMap<Long, BodyWeightEntity>,
    val bodyWeightArray : MutableList<BodyWeightEntity>)
