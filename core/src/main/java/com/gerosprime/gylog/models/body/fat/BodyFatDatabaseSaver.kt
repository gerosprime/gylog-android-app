package com.gerosprime.gylog.models.body.fat

import io.reactivex.Single
import java.util.*

interface BodyFatDatabaseSaver {
    fun save(recordId: Long?, weight: Float, date: Date, notes: String)
            : Single<BodyFatSaveResult>
}