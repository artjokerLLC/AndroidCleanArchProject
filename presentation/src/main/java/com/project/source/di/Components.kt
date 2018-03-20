package com.project.source.di

import android.content.Context
import com.project.source.AndroidApplication
import com.project.source.data.di.NetworkModule
import com.project.source.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivitiesModule::class,
    FragmentsModule::class,
    PresentersModule::class,
    RouterModule::class,
    NetworkModule::class,
    ImplModule::class,
    SchedulersModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(app: AndroidApplication)
}