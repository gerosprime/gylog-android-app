package com.gerosprime.gylog.models.programs

import io.reactivex.Single
import javax.inject.Inject

class DefaultProgramsLoader @Inject constructor(): ProgramsLoader {

    override fun loadUserPrograms(): Single<LoadedProgramResult> {
        return Single.just(LoadedProgramResult(ArrayList(), ArrayList()))
    }
}