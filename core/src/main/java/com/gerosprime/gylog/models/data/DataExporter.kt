package com.gerosprime.gylog.models.data

import io.reactivex.Single

interface DataExporter {
    fun export() : Single<DataExportResult>
}
