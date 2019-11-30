package com.gerosprime.gylog.models.body.weight

import io.reactivex.Single

interface BodyWeightCacheLoader {
    fun load(bodyWeightId : Long) : Single<BodyWeightCacheLoadResult>

    fun loadAll() : Single<AllBodyWeightsCacheLoadResult>
}