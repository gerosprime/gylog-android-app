package com.gerosprime.gylog.components.di.modules

import androidx.lifecycle.ViewModelProvider
import com.gerosprime.gylog.base.components.di.DefaultViewModelFactory
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