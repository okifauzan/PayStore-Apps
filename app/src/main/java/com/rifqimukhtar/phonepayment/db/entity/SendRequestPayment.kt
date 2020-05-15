package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName

class SendRequestPayment {
    @SerializedName("idUser")
    var idUser: String?

    @SerializedName("idBill")
    var idBill: Int?

    @SerializedName("idPaymentMethod")
    var idPaymentMethod: String?

    constructor(idUser: String?, idBill: Int?, idPaymentMethod: String?) {
        this.idUser = idUser
        this.idBill = idBill
        this.idPaymentMethod = idPaymentMethod
    }
}