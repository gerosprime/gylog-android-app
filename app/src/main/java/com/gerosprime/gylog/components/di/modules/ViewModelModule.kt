package com.gerosprime.gylog.components.di.modules

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gerosprime.gylog.base.components.di.ViewModelKey
import com.gerosprime.gylog.models.exercises.ExercisesLoader
import com.gerosprime.gylog.models.programs.EditProgramCacheSetterUseCase
import com.gerosprime.gylog.models.programs.ProgramsLoader
import com.gerosprime.gylog.models.workouts.DefaultWorkoutExerciseEditLoader
import com.gerosprime.gylog.models.workouts.WorkoutAddToCacheUseCase
import com.gerosprime.gylog.models.workouts.edit.WorkoutExerciseEditLoader
import com.gerosprime.gylog.models.workouts.edit.WorkoutExerciseEditToCacheUseCase
import com.gerosprime.gylog.models.workouts.edit.WorkoutSetExerciseCacheUC
import com.gerosprime.gylog.ui.exercises.add.DefaultWorkoutExerciseEditViewModel
import com.gerosprime.gylog.ui.exercises.dashboard.DefaultDashboardExercisesViewModel
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

}