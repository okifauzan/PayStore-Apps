package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName

class User {

    @SerializedName("status")
    var status: Int? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("phoneNumber")
    var phoneNumber: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("balance")
    var balance: Int? = null

    constructor(status: Int?, name: String?, phoneNumber: String?, email: String?, balance: Int?) {
        this.status = status
        this.name = name
        this.phoneNumber = phoneNumber
        this.email = email
        this.balance = balance
    }
}