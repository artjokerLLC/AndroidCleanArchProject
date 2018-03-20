package com.project.source.core.interactors

import com.project.source.core.BaseUseCase
import com.project.source.core.DataFacade
import com.project.source.core.NetworkFacade
import com.project.source.core.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AfterPaymentUseCase @Inject constructor(private val networkFacade: NetworkFacade,
                                              private val dateFacade: DataFacade,
                                              override val schedulers: Map<BaseUseCase.SchedulerType, Scheduler>)
    : UseCase.ParametrizedUseCase<AfterPaymentUseCase.Params, AfterPaymentUseCase.Result>() {

    override fun buildObservable(params: Params): Observable<Result> =
            networkFacade.reservPayment(params.reservation.id, params.reservation.totalPrice)
                    .toObservable()
                    .flatMap { processReservPayment(it, params) }
                    .startWith(Result.Loading(Result.State.PAYMENT_TICKETS))

    private fun processResult(params: Params) =
            networkFacade.reservSell(params.reservation.id)
                    .toObservable()
                    .flatMap { processReservSell(it, params) }
                    .startWith(Result.Loading(Result.State.PAYMENT_TICKETS_SELL))

    private fun processBadResult(params: Params): Observable<Result> {

        with(params) {

            val refundId = "${merchantId}_$lmiSysPaymentId"
            val hash = dateFacade.refundCommandHash(merchantId, lmiSysPaymentId, refundId, reservation.totalPrice)

            return networkFacade.refund(merchantId, lmiSysPaymentId, refundId, reservation.totalPrice, hash)
                    .flatMap {
                        networkFacade.reservClear(reservation.number).toObservable()
                                .map<Result> {
                                    Result.Error(Result.GetTicketsError(reservation.id.toString()))
                                }
                                .startWith(Result.Loading(Result.State.CLEAR_RESERV))
                    }
                    .startWith(Result.Loading(Result.State.REFUND))
        }
    }

    private fun processReservPayment(result: Int, params: Params) =
            when (result) {
                RESULT_SUCCESS -> processResult(params) //processBadResult(params)
                else -> processBadResult(params)
            }


    private fun processReservSell(sellReservation: SellReservationResult, params: Params) =
            when (sellReservation.result) {
                RESULT_SUCCESS -> Observable.just<Result>(Result.Success(sellReservation.sellSeats))
                else -> processBadResult(params)
            }


    companion object {
        const val RESULT_SUCCESS = 0
    }

    class Params(val merchantId: Int, val lmiSysPaymentId: String, val reservation: ReservationResult)

    sealed class Result {

        class Error(val e: GetTicketsError) : Result()

        class Success(val sellSeats: List<SellSeat>) : Result()

        class Loading(val state: State) : Result()

        enum class State {
            PAYMENT_TICKETS, PAYMENT_TICKETS_SELL, REFUND, CLEAR_RESERV
        }

        class GetTicketsError(msg: String) : Throwable(msg)
    }
}
