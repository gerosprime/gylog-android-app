package com.gerosprime.gylog.ui.programs.di

import com.gerosprime.gylog.models.programs.ProgramsLoader
import com.gerosprime.gylog.ui.programs.di.viewmodel.FakeProgramsLoader
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import org.mockito.Mockito
import javax.inject.Singleton

@Module
class ProgramsLoaderModule {

    @Provides
    @Singleton
    fun provideMockProgramsLoader() : ProgramsLoader {
        return FakeProgramsLoader()
    }

}