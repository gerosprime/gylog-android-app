package com.gerosprime.gylog.components.di.modules.models

import com.gerosprime.gylog.models.states.AddProgramEntityState
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

    @Provides
    @Singleton
    fun provideAddProgramCache() = AddProgramEntityState(null, null)

}