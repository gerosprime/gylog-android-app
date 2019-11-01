package com.gerosprime.gylog.models.programs

import com.gerosprime.gylog.models.states.ModelsCache
import io.reactivex.Single
import javax.inject.Inject

class DefaultProgramsLoader
    @Inject constructor(private val modelsCache: ModelsCache): ProgramsLoader {

    override fun loadUserPrograms(): Single<LoadedProgramResult> {
        return Single.just(LoadedProgramResult(modelsCache.programs, ArrayList()))
    }
}