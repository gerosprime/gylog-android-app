package com.gerosprime.gylog.components.di.modules.models

import com.gerosprime.gylog.models.database.GylogEntityDatabase
import com.gerosprime.gylog.models.states.*
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module
class CacheModule {

    @Provides
    @Singleton
    fun provideAddProgramCache() = EditProgramEntityCache(null,
        arrayListOf(), arrayListOf(), mutableMapOf(), arrayListOf(), arrayListOf())

    @Provides
    @Singleton
    fun provideModelCache() = ModelsCache(programs = arrayListOf(),
        programsMap = Collections.synchronizedMap(mutableMapOf()),
        workouts = arrayListOf(), workoutsMap = Collections.synchronizedMap(mutableMapOf()), 
        exercisesMap = Collections.synchronizedMap(mutableMapOf()), exercisesList = arrayListOf(),
        templateExercisesMap = Collections.synchronizedMap(mutableMapOf()), templateExercises = arrayListOf(),
        templateSets = arrayListOf(), templateSetsMap = Collections.synchronizedMap(mutableMapOf()),
        performedExercises = Collections.synchronizedMap(mutableMapOf()), performedSets = Collections.synchronizedMap(mutableMapOf()),
        bodyWeightMap = Collections.synchronizedMap(mutableMapOf()), bodyFatMap = Collections.synchronizedMap(mutableMapOf()))

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