package com.gerosprime.gylog.components.di.modules.models

import com.gerosprime.gylog.models.states.DefaultCacheClearUC
import com.gerosprime.gylog.models.states.EditCacheClearUC
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
    fun provideModelCache() = ModelsCache(programs = arrayListOf(),
        programsMap = mutableMapOf(),
        workouts = arrayListOf(), workoutsMap = mutableMapOf(), exercisesMap = mutableMapOf(),
        exercisesList = arrayListOf(), templateExercises = mutableMapOf(),
        performedExercises = mutableMapOf())

    @Provides
    @Singleton
    fun provideEditCacheClearUC(editProgramEntityCache: EditProgramEntityCache)
            : EditCacheClearUC
            = DefaultCacheClearUC(editProgramEntityCache)

}