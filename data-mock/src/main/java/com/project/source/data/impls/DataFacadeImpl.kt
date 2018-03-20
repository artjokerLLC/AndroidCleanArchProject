package com.project.source.data.impls

import com.project.source.core.DataFacade
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataFacadeImpl @Inject constructor() : DataFacade {

    override fun refundCommandHash(merchantId: Int, id: String, refundId: String, totalPrice: Float): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun paymentXML(merchantId: Int, paymentNo: String, paymentAmount: Float, paymentDesc: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

