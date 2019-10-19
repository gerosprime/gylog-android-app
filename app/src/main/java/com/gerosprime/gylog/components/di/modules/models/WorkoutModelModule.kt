package com.gerosprime.gylog.components.di.modules.models

import com.gerosprime.gylog.models.states.AddProgramEntityState
import com.gerosprime.gylog.models.workouts.DefaultWorkoutAddToCacheUC
import com.gerosprime.gylog.models.workouts.WorkoutAddToCacheUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WorkoutModelModule {

    @Provides
    @Singleton
    fun provideDefaultWorkoutAddCache(state: AddProgramEntityState)
            : WorkoutAddToCacheUseCase {
        return DefaultWorkoutAddToCacheUC(state)
    }

}