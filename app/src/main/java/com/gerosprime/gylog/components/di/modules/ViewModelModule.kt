package com.gerosprime.gylog.components.di.modules

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gerosprime.gylog.base.components.di.ViewModelKey
import com.gerosprime.gylog.models.exercises.ExercisesLoader
import com.gerosprime.gylog.models.exercises.templatesets.EditTemplateSetsCacheLoader
import com.gerosprime.gylog.models.exercises.templatesets.add.CreateTemplateSetToCacheUC
import com.gerosprime.gylog.models.exercises.templatesets.commit.CommitTemplatesToWorkoutUC
import com.gerosprime.gylog.models.exercises.templatesets.remove.RemoveTemplateSetFromCacheUC
import com.gerosprime.gylog.models.exercises.templatesets.single.TemplateSetEditCacheLoader
import com.gerosprime.gylog.models.exercises.templatesets.single.commit.TemplateSetCommitUC
import com.gerosprime.gylog.models.programs.edit.load.EditProgramCacheSetterUseCase
import com.gerosprime.gylog.models.programs.ProgramsLoader
import com.gerosprime.gylog.models.workouts.edit.add.WorkoutAddToCacheUseCase
import com.gerosprime.gylog.models.workouts.edit.load.WorkoutExerciseEditLoader
import com.gerosprime.gylog.models.workouts.edit.commit.WorkoutSetExerciseCacheUC
import com.gerosprime.gylog.ui.exercises.add.DefaultWorkoutExerciseEditViewModel
import com.gerosprime.gylog.ui.exercises.dashboard.DefaultDashboardExercisesViewModel
import com.gerosprime.gylog.ui.exercises.templatesets.DefaultEditTemplateSetsViewModel
import com.gerosprime.gylog.ui.exercises.templatesets.detail.DefaultTemplateSetEditViewModel
import com.gerosprime.gylog.ui.programs.DefaultProgramsDashboardViewModel
import com.gerosprime.gylog.ui.programs.add.DefaultProgramsAddViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module(includes = [ViewModelFactoryModule::class])
class ViewModelModule {

    @Provides
    @IntoMap
    @ViewModelKey(DefaultProgramsDashboardViewModel::class)
    fun provideDefaultProgramsDashboardViewModel(programsLoader: ProgramsLoader) : ViewModel {
        return DefaultProgramsDashboardViewModel(MutableLiveData(), MutableLiveData(),
            MutableLiveData(), MutableLiveData(), programsLoader, Schedulers.io(),
            AndroidSchedulers.mainThread())
    }

    @Provides
    @IntoMap
    @ViewModelKey(DefaultDashboardExercisesViewModel::class)
    fun provideDefaultDashboardExercisesViewModel(exercisesLoader: ExercisesLoader) : ViewModel {
        return DefaultDashboardExercisesViewModel(MutableLiveData(), MutableLiveData(),
            MutableLiveData(), exercisesLoader, Schedulers.io(),
            AndroidSchedulers.mainThread())
    }

    @Provides
    @IntoMap
    @ViewModelKey(DefaultProgramsAddViewModel::class)
    fun provideDefaultProgramsAddViewModel(workoutAddTCUC: WorkoutAddToCacheUseCase,
                                           editProgramCSUC: EditProgramCacheSetterUseCase
    ) : ViewModel {
        return DefaultProgramsAddViewModel(
            MutableLiveData(), MutableLiveData(),
            workoutAddTCUC, editProgramCSUC, AndroidSchedulers.mainThread(),
            Schedulers.io())
    }

    @Provides
    @IntoMap
    @ViewModelKey(DefaultWorkoutExerciseEditViewModel::class)
    fun provideDefaultWorkoutExerciseEditViewModel(exerciseEditLoader: WorkoutExerciseEditLoader,
                                                   workoutSetExerciseCacheUC: WorkoutSetExerciseCacheUC
    ) : ViewModel {
        return DefaultWorkoutExerciseEditViewModel(
            MutableLiveData(), MutableLiveData(),
            MutableLiveData(), exerciseEditLoader, workoutSetExerciseCacheUC,
            AndroidSchedulers.mainThread(), Schedulers.io())
    }

    @Provides
    @IntoMap
    @ViewModelKey(DefaultEditTemplateSetsViewModel::class)
    fun provideDefaultEditTemplateSetsViewModel(
        editTemplateSetsCacheLoader: EditTemplateSetsCacheLoader,
        addTemplateSetsCache : CreateTemplateSetToCacheUC,
        removeTemplateSetsCache : RemoveTemplateSetFromCacheUC,
        commitTemplateSetsCache: CommitTemplatesToWorkoutUC
    ) : ViewModel {
        return DefaultEditTemplateSetsViewModel(
            MutableLiveData(), MutableLiveData(),
            MutableLiveData(), MutableLiveData(), MutableLiveData(),
            editTemplateSetsCacheLoader, addTemplateSetsCache,
            removeTemplateSetsCache, commitTemplateSetsCache,
            Schedulers.io(), AndroidSchedulers.mainThread())
    }

    @Provides
    @IntoMap
    @ViewModelKey(DefaultTemplateSetEditViewModel::class)
    fun provideDefaultTemplateSetEditViewModel(templateSetloader : TemplateSetEditCacheLoader,
                                               templateSetCommitter : TemplateSetCommitUC
                                               ) : ViewModel {
        return DefaultTemplateSetEditViewModel(
            MutableLiveData(), MutableLiveData(),
            MutableLiveData(), templateSetloader, templateSetCommitter,
            AndroidSchedulers.mainThread(),Schedulers.io())
    }

}