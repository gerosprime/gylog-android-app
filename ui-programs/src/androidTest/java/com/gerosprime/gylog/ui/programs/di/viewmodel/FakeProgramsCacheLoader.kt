package com.gerosprime.gylog.ui.programs.di.viewmodel

import com.gerosprime.gylog.models.programs.LoadedProgramCacheResult
import com.gerosprime.gylog.models.programs.ProgramEntity
import com.gerosprime.gylog.models.programs.ProgramsCacheLoader
import io.reactivex.Single

class FakeProgramsCacheLoader : ProgramsCacheLoader {
    override fun loadUserPrograms(): Single<LoadedProgramCacheResult> {

        var userList = listOf(ProgramEntity(0,
            "BLS Phase 1", "", ""),
            ProgramEntity(0,
                "BLS Phase 2", "", ""),
            ProgramEntity(0,
                "BLS Phase 3", "", ""),
            ProgramEntity(0,
                "BLS Phase 3", "", ""),
            ProgramEntity(0,
                "BLS Phase 3", "", ""),
            ProgramEntity(0,
                "BLS Phase 3", "", ""),
            ProgramEntity(0,
                "BLS Phase 3", "", ""),
            ProgramEntity(0,
                "BLS Phase 3", "", ""))

        return Single.just(LoadedProgramCacheResult(userList, userList))
    }
}