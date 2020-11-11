package com.gerosprime.gylog.models.body.weight

import io.reactivex.Single

interface BodyWeightCacheLoader {

    fun loadLatest() : Single<LatestBodyWeightLoadResult>

    fun load(bodyWeightId : Long) : Single<BodyWeightCacheLoadResult>

    fun loadAll() : Single<AllBodyWeightsCacheLoadResult>
}