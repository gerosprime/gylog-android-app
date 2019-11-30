package com.gerosprime.gylog.models.body.weight

import com.gerosprime.gylog.models.states.ModelsCache
import io.reactivex.Single
import java.text.DateFormat

class DefaultBodyWeightCacheLoader(private val modelsCache: ModelsCache,
                                   private val graphDateFormat : DateFormat)
    : BodyWeightCacheLoader {
    override fun load(bodyWeightId: Long): Single<BodyWeightCacheLoadResult> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadAll(): Single<AllBodyWeightsCacheLoadResult>
            = Single.fromCallable {

        val bodyWeightGraph : LinkedHashMap<String, Float> = linkedMapOf()
        for (mutableEntry in modelsCache.bodyWeightMap) {
            val value = mutableEntry.value
            bodyWeightGraph[graphDateFormat.format(value.dateLogged)] = value.weight
        }

        AllBodyWeightsCacheLoadResult(modelsCache.bodyWeightMap, bodyWeightGraph)
    }
}