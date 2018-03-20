package com.project.source.di.modules

import android.content.Context
import com.project.source.core.interactors.AfterPaymentUseCase
import com.project.source.core.interactors.EventsUseCase
import com.project.source.navigation.ShakRouter
import dagger.Module
import dagger.Provides

@Module
class PresentersModule {

    @Provides
    fun provideEventsPresenter(eventsUseCase: EventsUseCase, ticketsModel: TicketsModel,
                               shakRouter: ShakRouter): EventsPresenter =
            EventsPresenter(eventsUseCase, ticketsModel, shakRouter)

    @Provides
    fun provideSectorsPresenter(sectorsUseCase: SectorsUseCase, ticketsModel: TicketsModel,
                                shakRouter: ShakRouter): SectorsPresenter =
            SectorsPresenter(sectorsUseCase, ticketsModel, shakRouter)

    @Provides
    fun provideSeatsPresenter(sectorStructureUseCase: SectorStructureUseCase,
                              reservationUseCase: ReservationUseCase, ticketsModel: TicketsModel,
                              shakRouter: ShakRouter): SeatsPresenter =
            SeatsPresenter(sectorStructureUseCase, reservationUseCase, ticketsModel, shakRouter)

    @Provides
    fun providePaymentPresenter(paymentInteractor: PaymentInteractor, ticketsModel: TicketsModel,
                                shakRouter: ShakRouter, context: Context): PaymentPresenter =
            PaymentPresenter(paymentInteractor, ticketsModel, shakRouter, context)

    @Provides
    fun provideTicketsPresenter(afterPaymentUseCase: AfterPaymentUseCase,
                                clearReservationUseCase: ClearReservationUseCase,
                                ticketsModel: TicketsModel): TicketsPresenter =
            TicketsPresenter(afterPaymentUseCase, clearReservationUseCase, ticketsModel)

    @Provides
    fun provideTicketPresenter(context: Context, ticketsModel: TicketsModel, bitmapUtils: BitmapUtils) =
            TicketPresenter(context, ticketsModel, bitmapUtils)
}
