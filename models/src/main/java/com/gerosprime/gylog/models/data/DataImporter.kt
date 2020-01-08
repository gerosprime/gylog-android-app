package com.gerosprime.gylog.models.data

import android.content.Context
import io.reactivex.Single
import java.io.File

interface DataImporter {
    fun importFromAsset(fileName : String, context : Context) : Single<DataImportResult>
    fun importFromFile(file : File) : Single<DataImportResult>
}