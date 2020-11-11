package com.gerosprime.gylog.models.body.weight

import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.states.ModelsCache
import io.reactivex.Single
import java.util.*

class DefaultBodyWeightCacheLoader(private val modelsCache: ModelsCache,
                                   private val cacheBuilder: ModelCacheBuilder)
    : BodyWeightCacheLoader {

    override fun loadLatest(): Single<LatestBodyWeightLoadResult> {
        return cacheBuilder.build().andThen(Single.fromCallable {

            var latest : BodyWeightEntity? = null

            modelsCache.bodyWeightMap.forEach { entry ->
                latest = if (latest == null) entry.value
                else {
                    val weight = entry.value
                    if (latest!!.dateLogged.before(weight.dateLogged)) weight
                    else latest
                }
            }

            latest = if (latest == null) BodyWeightEntity(null, 0f, Date(), "")
                     else latest

            LatestBodyWeightLoadResult(latest!!)

        })
    }

    override fun load(bodyWeightId: Long): Single<BodyWeightCacheLoadResult> {
        return cacheBuilder.build().andThen(Single.fromCallable {
            BodyWeightCacheLoadResult(modelsCache.bodyWeightMap[bodyWeightId]!!)
        })
    }

    override fun loadAll(): Single<AllBodyWeightsCacheLoadResult>
            = cacheBuilder.build().andThen(Single.fromCallable {

        val bodyWeightList : MutableList<BodyWeightEntity> = mutableListOf()
        for (value in modelsCache.bodyWeightMap.values) {
            bodyWeightList.add(value)
        }

        AllBodyWeightsCacheLoadResult(modelsCache.bodyWeightMap, bodyWeightList)
    })
}