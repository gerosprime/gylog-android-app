package com.gerosprime.gylog.components.di.modules.models

import com.gerosprime.gylog.models.body.fat.BodyFatCacheLoader
import com.gerosprime.gylog.models.body.fat.BodyFatDatabaseSaver
import com.gerosprime.gylog.models.body.fat.DefaultBodyFatCacheLoader
import com.gerosprime.gylog.models.body.fat.DefaultBodyFatDatabaseSaver
import com.gerosprime.gylog.models.database.GylogEntityDatabase
import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.states.ModelsCache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BodyFatModule {

    @Provides
    @Singleton
    fun provideBodyFatLoader(modelsCache: ModelsCache, cacheBuilder: ModelCacheBuilder)
            : BodyFatCacheLoader {
        return DefaultBodyFatCacheLoader(modelsCache, cacheBuilder)
    }

    @Provides
    @Singleton
    fun provideBodyFatSaver(modelsCache: ModelsCache,
                            database: GylogEntityDatabase,
                            cacheBuilder: ModelCacheBuilder) : BodyFatDatabaseSaver {
        return DefaultBodyFatDatabaseSaver(modelsCache, database, cacheBuilder)
    }


}