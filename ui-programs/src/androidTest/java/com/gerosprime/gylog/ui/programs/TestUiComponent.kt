package com.gerosprime.gylog.ui.programs

import com.gerosprime.gylog.ui.programs.di.FragmentBuilderModule
import com.gerosprime.gylog.ui.programs.di.ProgramsLoaderModule
import com.gerosprime.gylog.ui.programs.di.viewmodel.ViewModelModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [ProgramsLoaderModule::class, AndroidSupportInjectionModule::class,
    FragmentBuilderModule::class, ViewModelModule::class])
@Singleton
interface TestUiComponent {
    fun inject(test : DashboardProgramsFragmentTest)
    fun inject(app : TestUiProgramsApplication)
}