package com.gerosprime.gylog.models.programs

import io.reactivex.Single

interface ProgramsCacheLoader {
    fun loadUserPrograms() : Single<LoadedProgramCacheResult>
}