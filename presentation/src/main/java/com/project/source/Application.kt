package com.project.source

import android.app.Activity
import android.support.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import com.orhanobut.hawk.Hawk
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.fabric.sdk.android.Fabric
import javax.inject.Inject

class AndroidApplication : MultiDexApplication(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
        DaggerAppComponent
                .builder()
                .context(this)
                .build()
                .inject(this)
        Hawk.init(this).build()
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector
}