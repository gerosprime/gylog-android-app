package com.gerosprime.gylog.components.di.modules.models

import com.gerosprime.gylog.models.states.EditProgramEntityCache
import com.gerosprime.gylog.models.states.ModelsCache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

    @Provides
    @Singleton
    fun provideAddProgramCache() = EditProgramEntityCache(null,
        arrayListOf(), arrayListOf(), mutableMapOf(), arrayListOf())

    @Provides
    @Singleton
    fun provideModelCache() = ModelsCache(programsMap = mutableMapOf(),
        workouts = mutableMapOf(), exercisesMap = mutableMapOf(),
        exercisesList = arrayListOf(), templateExercises = mutableMapOf(),
        performedExercises = mutableMapOf())

}