package com.gerosprime.gylog.components.di.modules.models

import com.gerosprime.gylog.models.programs.DefaultEditProgramCacheSetterUC
import com.gerosprime.gylog.models.programs.DefaultProgramsLoader
import com.gerosprime.gylog.models.programs.EditProgramCacheSetterUseCase
import com.gerosprime.gylog.models.programs.ProgramsLoader
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ProgramsModelModule {

    @Binds
    @Singleton
    fun provideDefaultProgramsLoader(instance: DefaultProgramsLoader) : ProgramsLoader

    @Binds
    @Singleton
    fun provideDefaultNewProgramCacheSetterUC(instance : DefaultEditProgramCacheSetterUC)
            : EditProgramCacheSetterUseCase

}