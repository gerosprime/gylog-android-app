package com.gerosprime.gylog.components.di.modules.models

import com.gerosprime.gylog.models.body.weight.BodyWeightCacheLoader
import com.gerosprime.gylog.models.body.weight.BodyWeightDatabaseSaver
import com.gerosprime.gylog.models.body.weight.DefaultBodyWeightCacheLoader

import com.gerosprime.gylog.models.body.weight.DefaultBodyWeightDatabaseSaver
import com.gerosprime.gylog.models.database.GylogEntityDatabase
import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.states.ModelsCache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BodyWeightModule {

    @Provides
    @Singleton
    fun provideBodyWeightsLoader(modelsCache: ModelsCache, cacheBuilder: ModelCacheBuilder)
            : BodyWeightCacheLoader {
        return DefaultBodyWeightCacheLoader(modelsCache, cacheBuilder)
    }

    @Provides
    @Singleton
    fun provideBodyWeightSaver(modelsCache: ModelsCache,
                               database: GylogEntityDatabase,
                               cacheBuilder: ModelCacheBuilder) : BodyWeightDatabaseSaver {
        return DefaultBodyWeightDatabaseSaver(modelsCache, database, cacheBuilder)
    }

}