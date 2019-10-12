package com.gerosprime.gylog.components.di

import com.gerosprime.gylog.GylogApplication
import com.gerosprime.gylog.components.di.modules.ExercisesModelModule
import com.gerosprime.gylog.components.di.modules.FragmentBuilders
import com.gerosprime.gylog.components.di.modules.ProgramsModelModule
import com.gerosprime.gylog.components.di.modules.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [FragmentBuilders::class, ViewModelModule::class,
    ProgramsModelModule::class, ExercisesModelModule::class,
    AndroidInjectionModule::class, AndroidSupportInjectionModule::class])
@Singleton
interface GylogComponent {
    fun inject(application: GylogApplication)
}