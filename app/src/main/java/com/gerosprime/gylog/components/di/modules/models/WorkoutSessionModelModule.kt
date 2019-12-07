package com.gerosprime.gylog.components.di.modules.models

import com.gerosprime.gylog.models.database.GylogEntityDatabase
import com.gerosprime.gylog.models.states.ModelCacheBuilder
import com.gerosprime.gylog.models.states.ModelsCache
import com.gerosprime.gylog.models.states.RunningWorkoutSessionCache
import com.gerosprime.gylog.models.workouts.runningsession.create.DefaultWorkoutSessionCreator
import com.gerosprime.gylog.models.workouts.runningsession.create.WorkoutSessionCreator
import com.gerosprime.gylog.models.workouts.runningsession.discard.DefaultRunningWorkoutSessionDiscardUC
import com.gerosprime.gylog.models.workouts.runningsession.discard.RunningWorkoutSessionDiscardUC
import com.gerosprime.gylog.models.workouts.runningsession.finalizer.DefaultRunningWorkoutSessionFinalizer
import com.gerosprime.gylog.models.workouts.runningsession.finalizer.RunningWorkoutSessionFinalizer
import com.gerosprime.gylog.models.workouts.runningsession.load.DefaultRunningWorkoutSessionLoader
import com.gerosprime.gylog.models.workouts.runningsession.load.RunningWorkoutSessionLoader
import com.gerosprime.gylog.models.workouts.runningsession.performedset.add.AddPerformedSetUC
import com.gerosprime.gylog.models.workouts.runningsession.performedset.add.DefaultAddPerformedSetUC
import com.gerosprime.gylog.models.workouts.runningsession.performedset.edit.DefaultEditPerformedSetUC
import com.gerosprime.gylog.models.workouts.runningsession.performedset.edit.EditPerformedSetUC
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.DefaultRemovePerformedSetUC
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.DefaultUnRemovePerformedSetUC
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.RemovePerformedSetUC
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.UnRemovePerformedSetUC
import com.gerosprime.gylog.models.workouts.runningsession.save.DefaultWorkoutSessionSaver
import com.gerosprime.gylog.models.workouts.runningsession.save.WorkoutSessionSaver
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WorkoutSessionModelModule {

    @Provides
    @Singleton
    fun provideDefaultCreateWorkoutSessionUC(modelsCache: ModelsCache,
                                             runningSessionCache: RunningWorkoutSessionCache,
                                             cacheBuilder: ModelCacheBuilder
    )
            : WorkoutSessionCreator = DefaultWorkoutSessionCreator(modelsCache,
        runningSessionCache, cacheBuilder)

    @Singleton
    @Provides
    fun provideDefaultRunningWorkoutSessionLoader(cacheBuilder: ModelCacheBuilder,
                                                  modelsCache: ModelsCache,
                                                  sessionCache: RunningWorkoutSessionCache)
            : RunningWorkoutSessionLoader
            = DefaultRunningWorkoutSessionLoader(cacheBuilder, modelsCache, sessionCache)

    @Singleton
    @Provides
    fun provideDefaultAddPerformedSetUC(sessionCache: RunningWorkoutSessionCache)
            : AddPerformedSetUC = DefaultAddPerformedSetUC(sessionCache)

    @Singleton
    @Provides
    fun provideDefaultRemovePerformedSetUC(sessionCache: RunningWorkoutSessionCache)
            : RemovePerformedSetUC = DefaultRemovePerformedSetUC(sessionCache)

    @Singleton
    @Provides
    fun provideDefaultUnRemovePerformedSetUC(sessionCache: RunningWorkoutSessionCache)
            : UnRemovePerformedSetUC = DefaultUnRemovePerformedSetUC(sessionCache)

    @Singleton
    @Provides
    fun provideDefaultEditPerformedSetUC(sessionCache: RunningWorkoutSessionCache)
            : EditPerformedSetUC = DefaultEditPerformedSetUC(sessionCache)

    @Singleton
    @Provides
    fun provideDefaultWorkoutSessionFinalizer(sessionCache: RunningWorkoutSessionCache)
            : RunningWorkoutSessionFinalizer = DefaultRunningWorkoutSessionFinalizer(sessionCache)

    @Singleton
    @Provides
    fun provideDefaultWorkoutSessionSaver(modelsCache: ModelsCache, cacheBuilder: ModelCacheBuilder,
                                          database : GylogEntityDatabase)
            : WorkoutSessionSaver = DefaultWorkoutSessionSaver(modelsCache, cacheBuilder,
        database)

    @Singleton
    @Provides
    fun provideDefaultRunningWorkoutSessionDiscardUC(sessionCache: RunningWorkoutSessionCache)
            : RunningWorkoutSessionDiscardUC = DefaultRunningWorkoutSessionDiscardUC(sessionCache)

}