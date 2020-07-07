package com.gerosprime.gylog.models.body.fat

import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.states.ModelsCache
import io.reactivex.Single
import java.util.*

class DefaultBodyFatCacheLoader(private val modelsCache: ModelsCache,
                                   private val cacheBuilder: ModelCacheBuilder)
    : BodyFatCacheLoader {

    override fun loadLatest(): Single<LatestBodyFatLoadResult> = Single.fromCallable {
        var latest : BodyFatEntity? = null

        modelsCache.bodyFatMap.forEach { entry ->
            latest = if (latest == null) entry.value
            else {
                val weight = entry.value
                if (latest!!.dateLogged.before(weight.dateLogged)) weight
                else latest
            }
        }

        latest = if (latest == null) BodyFatEntity(null, 0f, Date(), "")
        else latest

        LatestBodyFatLoadResult(latest!!)
    }

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