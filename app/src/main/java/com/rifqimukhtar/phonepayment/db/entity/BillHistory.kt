package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class BillHistory : Serializable {
    @SerializedName("idPayment")
    var idPayment: Int? = null

    @SerializedName("idUser")
    var idUser: Int? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("phoneNumber")
    var phoneNumber: String? = null

    @SerializedName("balance")
    var balance: Int? = null

    @SerializedName("idBill")
    var idBill: Int? = null

    @SerializedName("telephoneNumber")
    var telephoneNumber: String? = null

    @SerializedName("status")
    var status: String? = null

    @SerializedName("amount")
    var amount: Int? = null

    @SerializedName("idPaymentMethod")
    var idPaymentMethod: Int? = null

    @SerializedName("method")
    var method: String? = null

    @SerializedName("timestamp")
    var timestamp: String? = null

    constructor(
        idPayment: Int?,
        idUser: Int?,
        name: String?,
        phoneNumber: String?,
        balance: Int?,
        idBill: Int?,
        telephoneNumber: String?,
        status: String?,
        amount: Int?,
        idPaymentMethod: Int?,
        method: String?,
        timestamp: String?
    ) {
        this.idPayment = idPayment
        this.idUser = idUser
        this.name = name
        this.phoneNumber = phoneNumber
        this.balance = balance
        this.idBill = idBill
        this.telephoneNumber = telephoneNumber
        this.status = status
        this.amount = amount
        this.idPaymentMethod = idPaymentMethod
        this.method = method
        this.timestamp = timestamp
    }
}