package com.project.source.di.modules

import com.project.source.core.ConnectionStateChangesRepo
import com.project.source.core.DataFacade
import com.project.source.core.LocalStorage
import com.project.source.core.NetworkFacade
import com.project.source.models.LocalStorageImpl
import dagger.Binds
import dagger.Module


@Module
abstract class ImplModule {

    @Binds
    abstract fun provideLocalStorage(localStorage: LocalStorageImpl): LocalStorage

    @Binds
    abstract fun provideNetworkFacade(networkFacadeImpl: NetworkFacadeImpl): NetworkFacade

    @Binds
    abstract fun provideConnectionStateChangesRepo(connectionStateChangesRepoImpl: ConnectionStateChangesRepoImpl): ConnectionStateChangesRepo

    @Binds
    abstract fun provideDataFacade(dataFacadeImpl: DataFacadeImpl): DataFacade
}
