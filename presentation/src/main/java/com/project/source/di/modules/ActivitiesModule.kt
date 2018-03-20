package com.project.source.di.modules

import com.project.source.ui.dashboard.DashboardActivity
import com.project.source.ui.initial.SplashActivity
import com.project.source.ui.initial.enter.EnterActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivitiesModule {

    @ContributesAndroidInjector
    fun splashActivityInjector(): SplashActivity

    @ContributesAndroidInjector
    fun initialActivityInjector(): EnterActivity

    @ContributesAndroidInjector
    fun dashboardActivityInjector(): DashboardActivity

    @ContributesAndroidInjector
    fun buyTicketsActivityInjector(): BuyTicketsActivity

    @ContributesAndroidInjector
    fun ticketActivityInjector(): TicketActivity
}
