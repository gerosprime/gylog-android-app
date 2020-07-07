package com.gerosprime.gylog.models.body.fat

import io.reactivex.Single

interface BodyFatCacheLoader {

    fun loadLatest() : Single<LatestBodyFatLoadResult>

    fun load(bodyWeightId : Long) : Single<BodyFatCacheLoadResult>

    fun loadAll() : Single<AllBodyFatsCacheLoadResult>
}