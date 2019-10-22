package com.gerosprime.gylog.components.di.modules.models

import com.gerosprime.gylog.models.exercises.DefaultExercisesLoader
import com.gerosprime.gylog.models.exercises.ExercisesLoader
import com.gerosprime.gylog.models.exercises.templatesets.DefaultEditTemplateSetsCacheLoader
import com.gerosprime.gylog.models.exercises.templatesets.EditTemplateSetsCacheLoader
import com.gerosprime.gylog.models.exercises.templatesets.add.CreateTemplateSetToCacheUC
import com.gerosprime.gylog.models.exercises.templatesets.add.DefaultCreateTemplateSetToCacheUC
import com.gerosprime.gylog.models.exercises.templatesets.commit.CommitTemplatesToWorkoutUC
import com.gerosprime.gylog.models.exercises.templatesets.commit.DefaultCommitTemplatesToWorkoutUC
import com.gerosprime.gylog.models.exercises.templatesets.remove.DefaultRemoveTemplateFromCacheUC
import com.gerosprime.gylog.models.exercises.templatesets.remove.RemoveTemplateSetFromCacheUC
import com.gerosprime.gylog.models.exercises.templatesets.single.DefaultTemplateSetEditCacheLoader
import com.gerosprime.gylog.models.exercises.templatesets.single.TemplateSetEditCacheLoader
import com.gerosprime.gylog.models.exercises.templatesets.single.commit.DefaultTemplateSetCommitCacheUC
import com.gerosprime.gylog.models.exercises.templatesets.single.commit.TemplateSetCommitUC
import com.gerosprime.gylog.models.states.EditProgramEntityCache
import com.gerosprime.gylog.models.states.ModelsCache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ExercisesModelModule {

    @Provides
    @Singleton
    fun provideDefaultExercisesLoader(modelsCache: ModelsCache) : ExercisesLoader {
        return DefaultExercisesLoader(modelsCache)
    }

    @Provides
    @Singleton
    fun provideDefaultEditTemplateSetsCacheLoader(editCache: EditProgramEntityCache) : EditTemplateSetsCacheLoader {
        return DefaultEditTemplateSetsCacheLoader(editCache)
    }

    @Provides
    @Singleton
    fun provideDefaultCreateTemplateSetToCacheUC(editCache: EditProgramEntityCache) : CreateTemplateSetToCacheUC {
        return DefaultCreateTemplateSetToCacheUC(editCache)
    }

    @Provides
    @Singleton
    fun provideDefaultRemoveTemplateFromCacheUC(editCache: EditProgramEntityCache)
            : RemoveTemplateSetFromCacheUC {
        return DefaultRemoveTemplateFromCacheUC(editCache)
    }

    @Provides
    @Singleton
    fun provideDefaultCommitTemplatesToWorkoutUC(editCache: EditProgramEntityCache)
            : CommitTemplatesToWorkoutUC {
        return DefaultCommitTemplatesToWorkoutUC(editCache)
    }

    @Provides
    @Singleton
    fun provideDefaultTemplateSetEditCacheLoader(editCache: EditProgramEntityCache)
            : TemplateSetEditCacheLoader {
        return DefaultTemplateSetEditCacheLoader(editCache)
    }

    @Provides
    @Singleton
    fun provideDefaultTemplateSetCommitUC(editCache: EditProgramEntityCache) : TemplateSetCommitUC {
        return DefaultTemplateSetCommitCacheUC(editCache)
    }





}