package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BillHistory : Serializable {
    @SerializedName("idInvoice")
    var idInvoice: Int? = null

    @SerializedName("amount")
    var amount: Int? = null

    @SerializedName("paymentType")
    var paymentType: Int? = null

    @SerializedName("status")
    var status: Int? = null

    @SerializedName("createdAt")
    var createdAt: String? = null

    constructor(idInvoice: Int?, amount: Int?, paymentType: Int?, status: Int?, createdAt: String?) {
        this.idInvoice = idInvoice
        this.amount = amount
        this.paymentType = paymentType
        this.status = status
        this.createdAt = createdAt
    }
}