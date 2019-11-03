package com.gerosprime.gylog.models.programs.detail

import com.gerosprime.gylog.models.states.ModelsCache
import io.reactivex.Single
import javax.inject.Inject

class DefaultProgramModelCacheLoader
    @Inject constructor(private val modelsCache: ModelsCache)
    : ProgramModelCacheLoader {

    override fun loadFromCache(programRecordId: Long): Single<LoadProgramFromCacheResult>
            = Single.fromCallable {
        LoadProgramFromCacheResult(modelsCache.programsMap[programRecordId], programRecordId)
    }

}