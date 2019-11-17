package com.gerosprime.gylog.components.di.modules.models

import android.content.Context
import androidx.room.Room
import com.gerosprime.gylog.models.database.GylogEntityDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context : Context) : GylogEntityDatabase {
        return Room.databaseBuilder(context, GylogEntityDatabase::class.java,
            "user-entity-database.db").build()
    }

}