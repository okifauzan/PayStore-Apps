package com.rifqimukhtar.phonepayment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rifqimukhtar.phonepayment.db.entity.*
import com.rifqimukhtar.phonepayment.repository.BillRepository

class BillViewModel(private var billRepository: BillRepository): ViewModel() {
    fun getPaymentDetail(sendPhone: SendPhone): LiveData<PhoneBill> {
        return billRepository.getPaymentDetail(sendPhone)
    }
    fun sendPaymentRequest(sendRequestPayment: SendRequestPayment): LiveData<BaseResponse<Any>> {
        return billRepository.sendPaymentRequest(sendRequestPayment)
    }
}