package com.gerosprime.gylog.models.programs.edit.load

import io.reactivex.Single

interface EditProgramCacheSetterUseCase {
    fun editProgramSetToCache(programId : Long?) : Single<EditProgramSetToCacheResult>
}