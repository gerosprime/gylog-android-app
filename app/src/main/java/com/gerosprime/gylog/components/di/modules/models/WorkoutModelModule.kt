package com.gerosprime.gylog.components.di.modules.models

import com.gerosprime.gylog.models.exercises.ExercisesLoader
import com.gerosprime.gylog.models.states.EditProgramEntityState
import com.gerosprime.gylog.models.states.ModelsCache
import com.gerosprime.gylog.models.workouts.DefaultWorkoutAddToCacheUC
import com.gerosprime.gylog.models.workouts.DefaultWorkoutExerciseEditLoader
import com.gerosprime.gylog.models.workouts.WorkoutAddToCacheUseCase
import com.gerosprime.gylog.models.workouts.edit.DefaultWorkoutSetExerciseCacheUC
import com.gerosprime.gylog.models.workouts.edit.WorkoutExerciseEditLoader
import com.gerosprime.gylog.models.workouts.edit.WorkoutSetExerciseCacheUC
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WorkoutModelModule {

    @Provides
    @Singleton
    fun provideDefaultWorkoutAddCache(state: EditProgramEntityState)
            : WorkoutAddToCacheUseCase {
        return DefaultWorkoutAddToCacheUC(state)
    }

    @Provides
    @Singleton
    fun provideDefaultWorkoutExerciseEditLoader(editProgramEntityState: EditProgramEntityState,
                                                modelsCache: ModelsCache,
                                                exercisesLoader: ExercisesLoader)
            : WorkoutExerciseEditLoader {
        return DefaultWorkoutExerciseEditLoader(editProgramEntityState,
            modelsCache, exercisesLoader)
    }

    @Provides
    @Singleton
    fun provideDefaultWorkoutSetExerciseCacheUC(state: EditProgramEntityState)
            : WorkoutSetExerciseCacheUC {
        return DefaultWorkoutSetExerciseCacheUC(state)
    }

}