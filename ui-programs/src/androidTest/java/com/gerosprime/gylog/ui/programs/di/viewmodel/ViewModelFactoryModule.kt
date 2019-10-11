package com.gerosprime.gylog.ui.programs.di.viewmodel

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ViewModelFactoryModule {
    @Binds
    @Singleton
    fun bindViewModelFactory(factory: DefaultViewModelFactory):
            ViewModelProvider.Factory
}