package com.project.source.core

import io.reactivex.Observable
import io.reactivex.Single

interface LocalStorage {
    fun internetConnected(): Boolean
}

interface NetworkFacade {
    //fun login(login: String, password: String): Single<String>

    fun events(): Single<List<Event>>
    fun sectors(eventId: Int): Single<List<Sector>>
    fun seats(sectorId: Int, eventId: Int): Single<List<Seat>>
    fun reservation(sectorId: Int, eventId: Int, seatsIds: String, fanId: Long?): Single<ReservationResult>
    fun reservPayment(reservationId: Int, amount: Float): Single<Int>
    fun reservSell(reservationId: Int): Single<SellReservationResult>
    fun reservClear(reservationNumber: String): Single<ClearReservationResult>
    fun refund(merchantId: Int, paymentId: String, refundId: String, amount: Float, hash: String?): Observable<RefundResult>
}

interface DataFacade {
    fun paymentXML(merchantId: Int, paymentNo: String, paymentAmount: Float, paymentDesc: String): String
    fun refundCommandHash(merchantId: Int, id: String, refundId: String, totalPrice: Float): String
}

interface ConnectionStateChangesRepo {
    fun getConnectionStateChanges(): Observable<Boolean>
}
