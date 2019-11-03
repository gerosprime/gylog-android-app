package com.gerosprime.gylog.models.programs.detail

import io.reactivex.Single


interface ProgramModelCacheLoader {
    fun loadFromCache(programRecordId : Long) : Single<LoadProgramFromCacheResult>
}