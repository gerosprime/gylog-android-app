package com.gerosprime.gylog.components.di.modules.models

import com.gerosprime.gylog.models.programs.edit.load.DefaultEditProgramCacheSetterUC
import com.gerosprime.gylog.models.programs.DefaultProgramsCacheCacheLoader
import com.gerosprime.gylog.models.programs.DefaultProgramsDatabaseLoader
import com.gerosprime.gylog.models.programs.edit.load.EditProgramCacheSetterUseCase
import com.gerosprime.gylog.models.programs.ProgramsCacheLoader
import com.gerosprime.gylog.models.programs.ProgramsDatabaseLoader
import com.gerosprime.gylog.models.programs.detail.DefaultProgramModelCacheLoader
import com.gerosprime.gylog.models.programs.detail.ProgramModelCacheLoader
import com.gerosprime.gylog.models.programs.edit.commit.CommitEdittedProgramCacheUC
import com.gerosprime.gylog.models.programs.edit.commit.DefaultCommitEdittedProgramCacheUC
import com.gerosprime.gylog.models.programs.save.DefaultSaveProgramDatabaseUC
import com.gerosprime.gylog.models.programs.save.SaveProgramDatabaseUC
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ProgramsModelModule {

    @Binds
    @Singleton
    fun provideDefaultProgramsLoader(instance: DefaultProgramsCacheCacheLoader) : ProgramsCacheLoader

    @Binds
    @Singleton
    fun provideDefaultNewProgramCacheSetterUC(instance : DefaultEditProgramCacheSetterUC)
            : EditProgramCacheSetterUseCase

    @Binds
    @Singleton
    fun provide(instance: DefaultCommitEdittedProgramCacheUC) : CommitEdittedProgramCacheUC

    @Binds
    @Singleton
    fun provideDefaultSaveProgramDatabaseUC(instance: DefaultSaveProgramDatabaseUC)
            : SaveProgramDatabaseUC

    @Binds
    @Singleton
    fun provideDefaultProgramCacheLoader(instance: DefaultProgramModelCacheLoader) : ProgramModelCacheLoader

    @Binds
    @Singleton
    fun provideDefaultProgramsDBLoader(instance : DefaultProgramsDatabaseLoader) : ProgramsDatabaseLoader

}