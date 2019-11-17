package com.gerosprime.gylog.ui.programs.di.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gerosprime.gylog.base.components.di.ViewModelKeyKt
import com.gerosprime.gylog.models.programs.ProgramsCacheLoader
import com.gerosprime.gylog.ui.programs.DefaultProgramsDashboardViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.reactivex.android.schedulers.AndroidSchedulers

@Module(includes = [ViewModelFactoryModule::class])
class ViewModelModule {

    @Provides
    @IntoMap
    @ViewModelKeyKt(DefaultProgramsDashboardViewModel::class)
    fun provideProgramViewModel(programsCacheLoader: ProgramsCacheLoader) : ViewModel {
        return DefaultProgramsDashboardViewModel(
            fetchStateLiveData = MutableLiveData(), userProgramListLiveData = MutableLiveData(),
            builtInProgramsLiveData = MutableLiveData(), errorLiveData = MutableLiveData(),
            backgroundScheduler = AndroidSchedulers.mainThread(), programsCacheLoader = programsCacheLoader,
            uiScheduler = AndroidSchedulers.mainThread())
    }



}