package com.gerosprime.gylog.models.programs

import io.reactivex.Single

interface NewProgramCacheSetterUseCase {
    fun newProgramSetToCache(entity: ProgramEntity) : Single<NewProgramSetToCacheResult>
}