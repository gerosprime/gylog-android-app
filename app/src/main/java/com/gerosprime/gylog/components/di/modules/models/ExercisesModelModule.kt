package com.gerosprime.gylog.components.di.modules.models

import com.gerosprime.gylog.models.exercises.DefaultExercisesLoader
import com.gerosprime.gylog.models.exercises.ExercisesLoader
import com.gerosprime.gylog.models.states.ModelsCache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ExercisesModelModule {

    @Provides
    @Singleton
    fun provideDefaultExercisesLoader(modelsCache: ModelsCache) : ExercisesLoader {
        return DefaultExercisesLoader(modelsCache)
    }

}