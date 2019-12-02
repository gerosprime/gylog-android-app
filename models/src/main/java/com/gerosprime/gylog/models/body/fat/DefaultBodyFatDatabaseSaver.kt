package com.gerosprime.gylog.models.body.fat


import com.gerosprime.gylog.models.database.GylogEntityDatabase
import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.states.ModelsCache
import io.reactivex.Single
import java.util.*

class DefaultBodyFatDatabaseSaver (private val modelCache : ModelsCache,
                                      private val database : GylogEntityDatabase,
                                      private val cacheBuilder: ModelCacheBuilder)
    : BodyFatDatabaseSaver {

    override fun save(
        recordId: Long?,
        fat: Float,
        date: Date,
        notes: String
    ): Single<BodyFatSaveResult> {
        return cacheBuilder.build()
            .andThen(Single.fromCallable {

                var loggedFat = modelCache.bodyFatMap[recordId]

                if (loggedFat != null) {
                    loggedFat.fat = fat
                    loggedFat.dateLogged = date
                    loggedFat.note = notes
                    database.bodyFatEntityDao().save(loggedFat)

                } else {
                    loggedFat = BodyFatEntity(null, fat, date, notes)
                    loggedFat.recordId = database.bodyFatEntityDao()
                        .save(loggedFat)
                    modelCache.bodyFatMap[loggedFat.recordId!!] = loggedFat
                }
                BodyFatSaveResult(loggedFat)

            })
    }
}