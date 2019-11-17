package com.gerosprime.gylog.components.di.modules.models

import com.gerosprime.gylog.models.states.DefaultModelCacheBuilder
import com.gerosprime.gylog.models.database.GylogEntityDatabase
import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.states.*
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
        exercisesList = arrayListOf(),
        templateExercisesMap = mutableMapOf(), templateExercises = arrayListOf(),
        templateSets = arrayListOf(), templateSetsMap = mutableMapOf(),
        performedExercises = mutableMapOf())

    @Provides
    @Singleton
    fun provideEditCacheClearUC(editProgramEntityCache: EditProgramEntityCache)
            : EditCacheClearUC
            = DefaultCacheClearUC(editProgramEntityCache)

    @Provides
    @Singleton
    fun provideWorkoutSessionCache() = RunningWorkoutSessionCache(null, null)

    @Provides
    @Singleton
    fun provideModelCacheBuilder(modelCache : ModelsCache,
                                 database : GylogEntityDatabase) : ModelCacheBuilder
            = DefaultModelCacheBuilder(modelCache, database)


}