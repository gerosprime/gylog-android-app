package com.gerosprime.gylog.components.di

import android.content.Context
import com.gerosprime.gylog.GylogApplication
import com.gerosprime.gylog.components.di.modules.ActivityBuilders
import com.gerosprime.gylog.components.di.modules.FragmentBuilders
import com.gerosprime.gylog.components.di.modules.ServiceBuilders
import com.gerosprime.gylog.components.di.modules.ViewModelModule
import com.gerosprime.gylog.components.di.modules.models.ModelsModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [ActivityBuilders::class, ServiceBuilders::class,
    FragmentBuilders::class, ViewModelModule::class,
    ModelsModule::class,
    AndroidInjectionModule::class, AndroidSupportInjectionModule::class])
@Singleton
interface GylogComponent {
    fun inject(application: GylogApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context : Context) : Builder

        fun build(): GylogComponent
    }

}