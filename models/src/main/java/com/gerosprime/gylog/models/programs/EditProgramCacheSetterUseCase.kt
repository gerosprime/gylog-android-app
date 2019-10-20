package com.gerosprime.gylog.models.programs

import io.reactivex.Single

interface EditProgramCacheSetterUseCase {
    fun editProgramSetToCache(entity: ProgramEntity) : Single<EditProgramSetToCacheResult>
}