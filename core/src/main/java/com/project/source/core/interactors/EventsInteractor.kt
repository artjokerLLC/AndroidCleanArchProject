package com.project.source.core.interactors

import com.project.source.core.NetworkFacade
import com.project.source.core.SingleUseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventsUseCase @Inject constructor(private val networkFacade: NetworkFacade,
                                        override val schedulers: Map<SchedulerType, Scheduler>)
    : SingleUseCase.SimpleUseCase<List<Event>>() {

    override fun buildSingle(params: Nothing?): Single<List<Event>> =
            networkFacade.events()
                    .map { it.sortedWith(compareBy(Event::start)) }

}
