package com.gerosprime.gylog.models.programs

import io.reactivex.Single

interface ProgramsLoader {
    fun loadUserPrograms() : Single<LoadedProgramResult>
}