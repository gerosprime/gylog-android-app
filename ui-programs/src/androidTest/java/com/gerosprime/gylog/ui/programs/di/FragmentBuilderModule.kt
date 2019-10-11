package com.gerosprime.gylog.ui.programs.di

import com.gerosprime.gylog.ui.programs.ProgramsDashboardFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeDashboardPrograms() : ProgramsDashboardFragment

}