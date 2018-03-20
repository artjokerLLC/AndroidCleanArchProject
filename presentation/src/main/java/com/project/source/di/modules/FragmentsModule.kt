package com.project.source.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentsModule {

    @ContributesAndroidInjector
    fun enterFragmentInjector(): EnterFragment

    @ContributesAndroidInjector
    fun signInFragmentInjector(): SignInFragment

    @ContributesAndroidInjector
    fun loadingFragmentInjector(): LoadingDialogFragment

    @ContributesAndroidInjector
    fun eventsFragmentInjector(): EventsFragment

    @ContributesAndroidInjector
    fun sectorsFragmentInjector(): SectorsFragment

    @ContributesAndroidInjector
    fun seatsFragmentInjector(): SeatsFragment

    @ContributesAndroidInjector
    fun paymentFragmentInjector(): PaymentFragment

    @ContributesAndroidInjector
    fun ticketsFragmentInjector(): TicketsFragment

    @ContributesAndroidInjector
    fun seatsPreviewDialogFragmentInjector(): SeatsPreviewDialogFragment
}
