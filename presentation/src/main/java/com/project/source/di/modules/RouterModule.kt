package com.project.source.di.modules

import com.project.source.navigation.ShakRouter
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Singleton

@Module
class RouterModule {

    private val cicerone by lazy { Cicerone.create(ShakRouter()) }

    @Provides
    @Singleton
    fun provideRouter(): ShakRouter = cicerone.router

    @Provides
    @Singleton
    fun provideNavigatorHolder(): NavigatorHolder = cicerone.navigatorHolder
}
