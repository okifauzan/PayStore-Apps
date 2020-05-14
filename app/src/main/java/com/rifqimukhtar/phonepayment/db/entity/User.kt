package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class User : Serializable {

    @SerializedName("idUser")
    var idUser: Int? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("password")
    var password: String? = null

    @SerializedName("phoneNumber")
    var phoneNumber: String? = null

    @SerializedName("balance")
    var balance: Int? = null

    @SerializedName("token")
    var token: Int? = null

    constructor(
        idUser: Int?,
        name: String?,
        email: String?,
        password: String?,
        phoneNumber: String?,
        balance: Int?,
        token: Int?
    ) {
        this.idUser = idUser
        this.name = name
        this.email = email
        this.password = password
        this.phoneNumber = phoneNumber
        this.balance = balance
        this.token = token
    }
}