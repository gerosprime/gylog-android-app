package com.gerosprime.gylog.ui.programs.di.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gerosprime.gylog.base.components.di.ViewModelKey
import com.gerosprime.gylog.base.components.di.ViewModelKeyKt
import com.gerosprime.gylog.models.programs.ProgramsLoader
import com.gerosprime.gylog.ui.programs.DefaultProgramsDashboardViewModel
import com.gerosprime.gylog.ui.programs.ProgramsDashboardViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Singleton

@Module(includes = [ViewModelFactoryModule::class])
class ViewModelModule {

    @Provides
    @IntoMap
    @ViewModelKeyKt(DefaultProgramsDashboardViewModel::class)
    fun provideProgramViewModel(programsLoader: ProgramsLoader) : ViewModel {
        return DefaultProgramsDashboardViewModel(
            fetchStateLiveData = MutableLiveData(), userProgramListLiveData = MutableLiveData(),
            builtInProgramsLiveData = MutableLiveData(), errorLiveData = MutableLiveData(),
            backgroundScheduler = AndroidSchedulers.mainThread(), programsLoader = programsLoader,
            uiScheduler = AndroidSchedulers.mainThread())
    }



}