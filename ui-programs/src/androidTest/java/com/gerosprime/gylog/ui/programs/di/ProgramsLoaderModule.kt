package com.gerosprime.gylog.ui.programs.di

import com.gerosprime.gylog.models.programs.ProgramsCacheLoader
import com.gerosprime.gylog.ui.programs.di.viewmodel.FakeProgramsCacheLoader
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProgramsLoaderModule {

    @Provides
    @Singleton
    fun provideMockProgramsLoader() : ProgramsCacheLoader {
        return FakeProgramsCacheLoader()
    }

}