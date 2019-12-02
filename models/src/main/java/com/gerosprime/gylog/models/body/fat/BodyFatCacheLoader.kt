package com.gerosprime.gylog.models.body.fat

import io.reactivex.Single

interface BodyFatCacheLoader {
    fun load(bodyWeightId : Long) : Single<BodyFatCacheLoadResult>

    fun loadAll() : Single<AllBodyFatsCacheLoadResult>
}