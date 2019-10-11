package com.gerosprime.gylog

import android.app.Activity
import android.app.Application
import android.app.Service
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import dagger.android.support.HasSupportFragmentInjector

class GylogApplication : Application(), HasActivityInjector,
    HasServiceInjector, HasSupportFragmentInjector {


    lateinit var dispatchingInjectorAct : DispatchingAndroidInjector<Activity>
    lateinit var dispatchingInjectorSer : DispatchingAndroidInjector<Service>
    lateinit var dispatchingInjectorFra : DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()

    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingInjectorAct

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingInjectorFra

    override fun serviceInjector(): AndroidInjector<Service> = dispatchingInjectorSer
}