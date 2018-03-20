package com.project.source.core.interactors

import com.project.source.core.BaseUseCase
import com.project.source.core.ConnectionStateChangesRepo
import com.project.source.core.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityChangesUseCase @Inject constructor(private val repo: ConnectionStateChangesRepo,
                                                     override val schedulers: Map<BaseUseCase.SchedulerType, Scheduler>)
    : UseCase.SimpleUseCase<Boolean>() {

    override fun buildObservable(params: Nothing?): Observable<Boolean> =
            repo.getConnectionStateChanges()
}
