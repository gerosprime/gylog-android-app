package com.gerosprime.gylog

import android.app.Application
import com.gerosprime.gylog.components.di.DaggerGylogComponent
import com.gerosprime.gylog.models.states.ModelsCache
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class GylogApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingInjectorAct : DispatchingAndroidInjector<Any>

    @Inject
    lateinit var modelsCache: ModelsCache

    override fun onCreate() {
        super.onCreate()
        DaggerGylogComponent.builder()
            .context(this).build().inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingInjectorAct

    override fun onTerminate() {
        modelsCache.clear()
        super.onTerminate()
    }
}