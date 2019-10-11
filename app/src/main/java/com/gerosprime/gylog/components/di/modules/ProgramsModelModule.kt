package com.gerosprime.gylog.components.di.modules

import com.gerosprime.gylog.models.programs.DefaultProgramsLoader
import com.gerosprime.gylog.models.programs.ProgramsLoader
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ProgramsModelModule {

    @Binds
    @Singleton
    fun provideDefaultProgramsLoader(defaultProgramsLoader: DefaultProgramsLoader) : ProgramsLoader

}