package com.gerosprime.gylog.ui.programs

import android.app.Application
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class TestUiProgramsApplication : Application(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatcherFragment : DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment>
            = dispatcherFragment

    override fun onCreate() {
        super.onCreate()
        DaggerTestUiComponent.create()
            .inject(this)
    }

}