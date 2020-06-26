package com.gerosprime.gylog.components.di.modules

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gerosprime.gylog.base.components.android.SingleLiveEvent
import com.gerosprime.gylog.base.components.di.ViewModelKey
import com.gerosprime.gylog.models.body.fat.BodyFatCacheLoader
import com.gerosprime.gylog.models.body.fat.BodyFatDatabaseSaver
import com.gerosprime.gylog.models.body.weight.BodyWeightCacheLoader
import com.gerosprime.gylog.models.body.weight.BodyWeightDatabaseSaver
import com.gerosprime.gylog.models.exercises.ExerciseDatabaseSaver
import com.gerosprime.gylog.models.exercises.ExercisesCacheLoader
import com.gerosprime.gylog.models.exercises.templatesets.EditTemplateSetsCacheLoader
import com.gerosprime.gylog.models.exercises.templatesets.add.CreateTemplateSetToCacheUC
import com.gerosprime.gylog.models.exercises.templatesets.commit.CommitTemplatesToWorkoutUC
import com.gerosprime.gylog.models.exercises.templatesets.remove.RemoveTemplateSetFromCacheUC
import com.gerosprime.gylog.models.exercises.templatesets.single.TemplateSetEditCacheLoader
import com.gerosprime.gylog.models.exercises.templatesets.single.commit.TemplateSetCommitUC
import com.gerosprime.gylog.models.programs.ProgramsCacheLoader
import com.gerosprime.gylog.models.programs.detail.ProgramModelCacheLoader
import com.gerosprime.gylog.models.programs.edit.commit.CommitEdittedProgramCacheUC
import com.gerosprime.gylog.models.programs.edit.load.EditProgramCacheSetterUseCase
import com.gerosprime.gylog.models.programs.save.SaveProgramDatabaseUC
import com.gerosprime.gylog.models.states.EditCacheClearUC
import com.gerosprime.gylog.models.workouts.detail.WorkoutCacheLoader
import com.gerosprime.gylog.models.workouts.edit.add.WorkoutAddToCacheUseCase
import com.gerosprime.gylog.models.workouts.edit.commit.WorkoutSetExerciseCacheUC
import com.gerosprime.gylog.models.workouts.edit.load.WorkoutExerciseEditLoader
import com.gerosprime.gylog.models.workouts.history.WorkoutExerciseHistoryLoader
import com.gerosprime.gylog.models.workouts.runningsession.create.WorkoutSessionCreator
import com.gerosprime.gylog.models.workouts.runningsession.discard.RunningWorkoutSessionDiscardUC
import com.gerosprime.gylog.models.workouts.runningsession.finalizer.RunningWorkoutSessionFinalizer
import com.gerosprime.gylog.models.workouts.runningsession.load.RunningWorkoutSessionLoader
import com.gerosprime.gylog.models.workouts.runningsession.performedset.add.AddPerformedSetUC
import com.gerosprime.gylog.models.workouts.runningsession.performedset.edit.EditPerformedSetUC
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.RemovePerformedSetUC
import com.gerosprime.gylog.models.workouts.runningsession.performedset.remove.UnRemovePerformedSetUC
import com.gerosprime.gylog.models.workouts.runningsession.save.WorkoutSessionSaver
import com.gerosprime.gylog.models.workouts.save.SaveWorkoutsDatabaseUC
import com.gerosprime.gylog.ui.exercises.add.DefaultExerciseAddViewModel
import com.gerosprime.gylog.ui.exercises.dashboard.DefaultDashboardExercisesViewModel
import com.gerosprime.gylog.ui.exercises.detail.DefaultExerciseDetailViewModel
import com.gerosprime.gylog.ui.exercises.templatesets.DefaultEditTemplateSetsViewModel
import com.gerosprime.gylog.ui.exercises.templatesets.detail.DefaultTemplateSetEditViewModel
import com.gerosprime.gylog.ui.programs.DefaultProgramsDashboardViewModel
import com.gerosprime.gylog.ui.programs.add.DefaultProgramsAddViewModel
import com.gerosprime.gylog.ui.programs.detail.DefaultProgramDetailViewModel
import com.gerosprime.gylog.ui.workouts.detail.DefaultWorkoutDetailViewModel
import com.gerosprime.gylog.ui.workouts.exercises.DefaultWorkoutExerciseEditViewModel
import com.gerosprime.gylog.ui.workouts.history.DefaultWorkoutExerciseHistoryViewModel
import com.gerosprime.gylog.ui.workouts.session.DefaultWorkoutSessionViewModel
import com.gerosprime.gylog.ui.workouts.session.info.DefaultSessionInfoViewModel
import com.gerosprime.ui.fat.DefaultBodyFatGraphViewModel
import com.gerosprime.ui.weight.DefaultBodyWeightGraphViewModel
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
    fun provideDefaultProgramsDashboardViewModel(programsCacheLoader: ProgramsCacheLoader) : ViewModel {
        return DefaultProgramsDashboardViewModel(MutableLiveData(), MutableLiveData(),
            MutableLiveData(), MutableLiveData(), programsCacheLoader, Schedulers.io(),
            AndroidSchedulers.mainThread())
    }

    @Provides
    @IntoMap
    @ViewModelKey(DefaultDashboardExercisesViewModel::class)
    fun provideDefaultDashboardExercisesViewModel(exercisesCacheLoader: ExercisesCacheLoader) : ViewModel {
        return DefaultDashboardExercisesViewModel(exercisesCacheLoader, Schedulers.io(),
            AndroidSchedulers.mainThread())
    }

    @Provides
    @IntoMap
    @ViewModelKey(DefaultProgramsAddViewModel::class)
    fun provideDefaultProgramsAddViewModel(workoutAddTCUC: WorkoutAddToCacheUseCase,
                                           editProgramCSUC: EditProgramCacheSetterUseCase,
                                           commitProgramCacheUseCase : CommitEdittedProgramCacheUC,
                                           saveProgramDatabaseUC: SaveProgramDatabaseUC,
                                           saveWorkoutsDatabaseUC: SaveWorkoutsDatabaseUC,
                                           clearCacheUC: EditCacheClearUC
                                           ) : ViewModel {
        return DefaultProgramsAddViewModel(
            workoutAddTCUC, editProgramCSUC, commitProgramCacheUseCase, saveProgramDatabaseUC,
            saveWorkoutsDatabaseUC, clearCacheUC,
            AndroidSchedulers.mainThread(),
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
            AndroidSchedulers.mainThread(), Schedulers.io()
        )
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

    @Provides
    @IntoMap
    @ViewModelKey(DefaultProgramDetailViewModel::class)
    fun provideDefaultProgramDetailViewModel(programModelCacheLoader: ProgramModelCacheLoader
                                            ): ViewModel {
        return DefaultProgramDetailViewModel(
            MutableLiveData(), MutableLiveData(),
            programModelCacheLoader,
            AndroidSchedulers.mainThread(), Schedulers.io())
    }

    @Provides
    @IntoMap
    @ViewModelKey(DefaultWorkoutDetailViewModel::class)
    fun provideDefaultWorkoutDetailViewModel(workoutDetaiLoader: WorkoutCacheLoader) : ViewModel {
        return DefaultWorkoutDetailViewModel(workoutDetaiLoader,
            AndroidSchedulers.mainThread(), Schedulers.io())
    }

    @Provides
    @IntoMap
    @ViewModelKey(DefaultWorkoutSessionViewModel::class)
    fun provideDefaultWorkoutSessionViewModel(createWorkoutSessionUC : WorkoutSessionCreator,
                                              runningWorkoutSessionLoader: RunningWorkoutSessionLoader,

                                              addPerformedSetUC: AddPerformedSetUC,
                                              removePerformedSetUC: RemovePerformedSetUC,
                                              unRemovePerformedSetUC: UnRemovePerformedSetUC,
                                              editPerformedSetUC: EditPerformedSetUC,
                                              sessionFinalizer: RunningWorkoutSessionFinalizer,
                                              sessionSaver : WorkoutSessionSaver,
                                              sessionDiscardUC : RunningWorkoutSessionDiscardUC)
            : ViewModel {
        return DefaultWorkoutSessionViewModel(MutableLiveData(), MutableLiveData(),
            MutableLiveData(), MutableLiveData(), SingleLiveEvent(),
            SingleLiveEvent(), SingleLiveEvent(), SingleLiveEvent(),
            SingleLiveEvent(), SingleLiveEvent(),
            SingleLiveEvent(), SingleLiveEvent(), SingleLiveEvent(),
            createWorkoutSessionUC, runningWorkoutSessionLoader,
            sessionFinalizer, sessionSaver, sessionDiscardUC,
            addPerformedSetUC, removePerformedSetUC, unRemovePerformedSetUC,
            editPerformedSetUC,
            AndroidSchedulers.mainThread(), Schedulers.io())
    }

    @Provides
    @IntoMap
    @ViewModelKey(DefaultExerciseAddViewModel::class)
    fun provideDefaultExerciseAddViewModel(exerciseDatabaseSaver: ExerciseDatabaseSaver,
                                           exercisesCacheLoader: ExercisesCacheLoader)
            : ViewModel {
        return DefaultExerciseAddViewModel(
            MutableLiveData(), MutableLiveData(), MutableLiveData(),
            AndroidSchedulers.mainThread(), Schedulers.io(),
            exerciseDatabaseSaver, exercisesCacheLoader)
    }

    @Provides
    @IntoMap
    @ViewModelKey(DefaultBodyWeightGraphViewModel::class)
    fun provideDefaultBodyWeightGraphViewModel(bodyWeightLoader : BodyWeightCacheLoader,
                                               bodyWeightSaver : BodyWeightDatabaseSaver
    )
            : ViewModel {
        return DefaultBodyWeightGraphViewModel(MutableLiveData(), SingleLiveEvent(),
            bodyWeightLoader, bodyWeightSaver,
            AndroidSchedulers.mainThread(), Schedulers.io())
    }

    @Provides
    @IntoMap
    @ViewModelKey(DefaultBodyFatGraphViewModel::class)
    fun provideDefaultBodyFatGraphViewModel(bodyFatLoader : BodyFatCacheLoader,
                                               bodyFatSaver : BodyFatDatabaseSaver
    )
            : ViewModel {
        return DefaultBodyFatGraphViewModel(MutableLiveData(), SingleLiveEvent(),
            bodyFatLoader, bodyFatSaver,
            AndroidSchedulers.mainThread(), Schedulers.io())
    }

    @Provides
    @IntoMap
    @ViewModelKey(DefaultSessionInfoViewModel::class)
    fun provideDefaultSessionInfoViewModel(
        runningWorkoutSessionLoader: RunningWorkoutSessionLoader
    )
            : ViewModel {
        return DefaultSessionInfoViewModel(MutableLiveData(), SingleLiveEvent(),
            runningWorkoutSessionLoader, AndroidSchedulers.mainThread(),
            Schedulers.io(), Schedulers.computation())
    }

    @Provides
    @IntoMap
    @ViewModelKey(DefaultExerciseDetailViewModel::class)
    fun provideDefaultExerciseDetailViewModel(
        exercisesCacheLoader: ExercisesCacheLoader
    )
            : ViewModel {
        return DefaultExerciseDetailViewModel(MutableLiveData(),
            exercisesCacheLoader, AndroidSchedulers.mainThread(), Schedulers.io())
    }

    @Provides
    @IntoMap
    @ViewModelKey(DefaultWorkoutExerciseHistoryViewModel::class)
    fun provideDefaultWorkoutExerciseHistoryViewModel(
        workoutExerciseHistoryLoader: WorkoutExerciseHistoryLoader
    )
            : ViewModel {
        return DefaultWorkoutExerciseHistoryViewModel(
            MutableLiveData(), MutableLiveData(),
            workoutExerciseHistoryLoader, AndroidSchedulers.mainThread(), Schedulers.io())
    }

}