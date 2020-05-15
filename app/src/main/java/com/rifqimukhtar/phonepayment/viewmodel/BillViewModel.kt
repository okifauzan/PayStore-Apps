package com.rifqimukhtar.phonepayment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rifqimukhtar.phonepayment.db.entity.*
import com.rifqimukhtar.phonepayment.repository.BillRepository

class BillViewModel(private var billRepository: BillRepository): ViewModel() {
    fun getPaymentDetail(sendUser: SendUser): LiveData<PhoneBill> {
        return billRepository.getPaymentDetail(sendUser)
    }
    fun sendPaymentRequest(sendRequestPayment: SendRequestPayment): LiveData<BaseResponse<Any>> {
        return billRepository.sendPaymentRequest(sendRequestPayment)
    }
}