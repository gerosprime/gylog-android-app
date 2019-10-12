package com.gerosprime.gylog.components.di.modules

import com.gerosprime.gylog.models.exercises.DefaultExercisesLoader
import com.gerosprime.gylog.models.exercises.ExercisesLoader
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ExercisesModelModule {

    @Provides
    @Singleton
    fun provideDefaultExercisesLoader() : ExercisesLoader {
        return DefaultExercisesLoader()
    }

}