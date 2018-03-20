package com.project.source.data.impls

import com.project.source.core.ConnectionStateChangesRepo
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectionStateChangesRepoImpl @Inject constructor() : ConnectionStateChangesRepo {

    override fun getConnectionStateChanges(): Observable<Boolean> {
        return Observable.just(true)
    }
}