package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SendRequestPayment : Serializable {
    @SerializedName("idBill")
    var idBill: Int? = null

    @SerializedName("idUser")
    var idUser: Int? = null

    @SerializedName("idPaymentMethod")
    var idPaymentMethod: Int? = null

    constructor(idBill: Int?, idUser: Int?, idPaymentMethod: Int?) {
        this.idBill = idBill
        this.idUser = idUser
        this.idPaymentMethod = idPaymentMethod
    }
}