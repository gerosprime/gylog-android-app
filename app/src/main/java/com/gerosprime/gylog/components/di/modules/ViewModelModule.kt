package com.gerosprime.gylog.components.di.modules

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gerosprime.gylog.base.components.di.ViewModelKey
import com.gerosprime.gylog.models.exercises.ExercisesLoader
import com.gerosprime.gylog.models.programs.NewProgramCacheSetterUseCase
import com.gerosprime.gylog.models.programs.ProgramsLoader
import com.gerosprime.gylog.models.workouts.WorkoutAddToCacheUseCase
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
                                           newProgramCSUC: NewProgramCacheSetterUseCase
    ) : ViewModel {
        return DefaultProgramsAddViewModel(
            MutableLiveData(), MutableLiveData(), MutableLiveData(),
            workoutAddTCUC, newProgramCSUC, AndroidSchedulers.mainThread(),
            Schedulers.io())
    }

}