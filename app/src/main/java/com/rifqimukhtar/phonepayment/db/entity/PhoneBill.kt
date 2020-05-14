package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PhoneBill : Serializable {

    @SerializedName("status")
    var status: Int? = null

    @SerializedName("telephoneNumber")
    var telephoneNumber: String? = null

    @SerializedName("telephoneOwner")
    var telephoneOwner: String? = null

    @SerializedName("amount")
    var amount: Int? = null

    constructor(status: Int?, telephoneNumber: String?, telephoneOwner: String?, amount: Int?) {
        this.status = status
        this.telephoneNumber = telephoneNumber
        this.telephoneOwner = telephoneOwner
        this.amount = amount
    }
}