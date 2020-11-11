package com.gerosprime.gylog.models.body.weight

import com.gerosprime.gylog.models.database.GylogEntityDatabase
import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.states.ModelsCache
import io.reactivex.Single
import java.util.*

class DefaultBodyWeightDatabaseSaver (private val modelCache : ModelsCache,
                                      private val database : GylogEntityDatabase,
                                      private val cacheBuilder: ModelCacheBuilder)
    : BodyWeightDatabaseSaver {

    override fun save(
        recordId: Long?,
        weight: Float,
        date: Date,
        notes: String
    ): Single<BodyWeightSaveResult> {
        return cacheBuilder.build()
            .andThen(Single.fromCallable {

                var loggedBodyWeight = modelCache.bodyWeightMap[recordId]

                if (loggedBodyWeight != null) {
                    loggedBodyWeight.weight = weight
                    loggedBodyWeight.dateLogged = date
                    loggedBodyWeight.note = notes
                    database.bodyWeightEntityDao().save(loggedBodyWeight)

                } else {
                    loggedBodyWeight = BodyWeightEntity(null, weight, date, notes)
                    loggedBodyWeight.recordId = database.bodyWeightEntityDao()
                        .save(loggedBodyWeight)
                    modelCache.bodyWeightMap[loggedBodyWeight.recordId!!] = loggedBodyWeight
                }
                BodyWeightSaveResult(loggedBodyWeight)

            })
    }
}