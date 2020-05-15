package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class PhoneBill : Serializable {

    @SerializedName("idBill")
    var idBill: Int? = null

    @SerializedName("telephoneOwner")
    var telephoneOwner: String? = null

    @SerializedName("telephoneNumber")
    var telephoneNumber: String? = null

    @SerializedName("month")
    var month: String? = null

    @SerializedName("amount")
    var amount: Int? = null

    @SerializedName("status")
    var status: String? = null

    constructor(
        idBill: Int?,
        telephoneOwner: String?,
        telephoneNumber: String?,
        month: String?,
        amount: Int?,
        status: String?
    ) {
        this.idBill = idBill
        this.telephoneOwner = telephoneOwner
        this.telephoneNumber = telephoneNumber
        this.month = month
        this.amount = amount
        this.status = status
    }
}