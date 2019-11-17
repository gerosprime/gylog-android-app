package com.gerosprime.gylog.components.di.modules.models

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class GylogModule(private val context: Context) {

    @Provides
    fun provideContext() : Context {
        return context
    }

}