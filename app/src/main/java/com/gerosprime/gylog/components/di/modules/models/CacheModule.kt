package com.gerosprime.gylog.components.di.modules.models

import com.gerosprime.gylog.models.states.EditProgramEntityState
import com.gerosprime.gylog.models.states.ModelsCache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

    @Provides
    @Singleton
    fun provideAddProgramCache() = EditProgramEntityState(null, null)

    @Provides
    @Singleton
    fun provideModelCache() = ModelsCache(programsMap = mutableMapOf(),
        workouts = mutableMapOf(), exercisesMap = mutableMapOf(),
        exercisesList = arrayListOf(), templateExercises = mutableMapOf(),
        performedExercises = mutableMapOf())

}