package com.gerosprime.gylog.components.di.modules.models

import com.gerosprime.gylog.models.exercises.ExercisesLoader
import com.gerosprime.gylog.models.states.EditProgramEntityCache
import com.gerosprime.gylog.models.states.ModelsCache
import com.gerosprime.gylog.models.workouts.detail.DefaultWorkoutCacheLoader
import com.gerosprime.gylog.models.workouts.detail.WorkoutCacheLoader
import com.gerosprime.gylog.models.workouts.edit.add.DefaultWorkoutAddToCacheUC
import com.gerosprime.gylog.models.workouts.edit.load.DefaultWorkoutExerciseEditLoader
import com.gerosprime.gylog.models.workouts.edit.add.WorkoutAddToCacheUseCase
import com.gerosprime.gylog.models.workouts.edit.commit.DefaultWorkoutSetExerciseCacheUC
import com.gerosprime.gylog.models.workouts.edit.load.WorkoutExerciseEditLoader
import com.gerosprime.gylog.models.workouts.edit.commit.WorkoutSetExerciseCacheUC
import com.gerosprime.gylog.models.workouts.save.DefaultSaveWorkoutsDatabaseUC
import com.gerosprime.gylog.models.workouts.save.SaveWorkoutsDatabaseUC
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WorkoutModelModule {

    @Provides
    @Singleton
    fun provideDefaultWorkoutAddCache(cache: EditProgramEntityCache)
            : WorkoutAddToCacheUseCase {
        return DefaultWorkoutAddToCacheUC(cache)
    }

    @Provides
    @Singleton
    fun provideDefaultWorkoutExerciseEditLoader(editProgramEntityCache: EditProgramEntityCache,
                                                modelsCache: ModelsCache,
                                                exercisesLoader: ExercisesLoader)
            : WorkoutExerciseEditLoader {
        return DefaultWorkoutExerciseEditLoader(
            editProgramEntityCache,
            modelsCache, exercisesLoader
        )
    }

    @Provides
    @Singleton
    fun provideDefaultWorkoutSetExerciseCacheUC(cache: EditProgramEntityCache)
            : WorkoutSetExerciseCacheUC {
        return DefaultWorkoutSetExerciseCacheUC(
            cache
        )
    }

    @Provides
    @Singleton
    fun provideDefaultWorkoutCacheLoader(modelsCache: ModelsCache) : WorkoutCacheLoader
        = DefaultWorkoutCacheLoader(modelsCache)

    @Provides
    @Singleton
    fun provideDefaultWorkoutSaver(modelsCache: ModelsCache) : SaveWorkoutsDatabaseUC
        = DefaultSaveWorkoutsDatabaseUC(modelsCache)


}