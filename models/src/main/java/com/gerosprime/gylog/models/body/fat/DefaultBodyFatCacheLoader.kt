package com.gerosprime.gylog.models.body.fat

import com.gerosprime.gylog.models.body.weight.BodyWeightEntity
import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.states.ModelsCache
import io.reactivex.Single

class DefaultBodyFatCacheLoader(private val modelsCache: ModelsCache,
                                   private val cacheBuilder: ModelCacheBuilder)
    : BodyFatCacheLoader {
    override fun load(bodyWeightId: Long): Single<BodyFatCacheLoadResult> {
        return cacheBuilder.build().andThen(Single.fromCallable {
            BodyFatCacheLoadResult(modelsCache.bodyWeightMap[bodyWeightId]!!)
        })
    }

    override fun loadAll(): Single<AllBodyFatsCacheLoadResult>
            = cacheBuilder.build().andThen(Single.fromCallable {

        val bodyWeightList : ArrayList<BodyFatEntity> = arrayListOf()
        for (value in modelsCache.bodyFatMap.values) {
            bodyWeightList.add(value)
        }

        AllBodyFatsCacheLoadResult(modelsCache.bodyFatMap, bodyWeightList)
    })
}