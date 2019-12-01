package com.gerosprime.gylog.models.body.weight

import com.gerosprime.gylog.models.database.GylogEntityDatabase
import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.states.ModelsCache
import io.reactivex.Single
import java.util.*
import kotlin.collections.ArrayList

class DefaultBodyWeightCacheLoader(private val modelsCache: ModelsCache,
                                   private val cacheBuilder: ModelCacheBuilder)
    : BodyWeightCacheLoader {
    override fun load(bodyWeightId: Long): Single<BodyWeightCacheLoadResult> {
        return cacheBuilder.build().andThen(Single.fromCallable {
            BodyWeightCacheLoadResult(modelsCache.bodyWeightMap[bodyWeightId]!!)
        })
    }

    override fun loadAll(): Single<AllBodyWeightsCacheLoadResult>
            = cacheBuilder.build().andThen(Single.fromCallable {

        val bodyWeightList : ArrayList<BodyWeightEntity> = arrayListOf()
        for (value in modelsCache.bodyWeightMap.values) {
            bodyWeightList.add(value)
        }

        AllBodyWeightsCacheLoadResult(modelsCache.bodyWeightMap, bodyWeightList)
    })
}