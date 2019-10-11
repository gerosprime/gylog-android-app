package com.gerosprime.gylog.components.di.modules

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gerosprime.gylog.base.components.di.ViewModelKey
import com.gerosprime.gylog.models.programs.ProgramsLoader
import com.gerosprime.gylog.ui.programs.DefaultProgramsDashboardViewModel
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

}