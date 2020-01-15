package com.gerosprime.gylog.components.di.modules

import com.gerosprime.gylog.ui.workouts.session.WorkoutSessionService
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceBuilders {

    @ContributesAndroidInjector
    abstract fun provideWorkoutSessionService() : WorkoutSessionService

}