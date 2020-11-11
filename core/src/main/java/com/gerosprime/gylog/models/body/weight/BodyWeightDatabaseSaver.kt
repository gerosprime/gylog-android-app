package com.gerosprime.gylog.models.body.weight

import io.reactivex.Single
import java.util.*

interface BodyWeightDatabaseSaver {
    fun save(recordId: Long?, weight: Float, date: Date, notes: String)
            : Single<BodyWeightSaveResult>
}