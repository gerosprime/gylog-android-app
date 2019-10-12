package com.gerosprime.gylog

import android.app.Activity
import android.app.Application
import android.app.Service
import androidx.fragment.app.Fragment
import com.gerosprime.gylog.components.di.DaggerGylogComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class GylogApplication : Application(), HasActivityInjector,
    HasServiceInjector, HasSupportFragmentInjector {


    @Inject
    lateinit var dispatchingInjectorAct : DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var dispatchingInjectorSer : DispatchingAndroidInjector<Service>
    @Inject
    lateinit var dispatchingInjectorFra : DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()
        DaggerGylogComponent.create().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingInjectorAct

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingInjectorFra

    override fun serviceInjector(): AndroidInjector<Service> = dispatchingInjectorSer
}