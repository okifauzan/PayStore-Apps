package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName

data class CreateAccountResponse (
    @SerializedName("idUser")
    var idUser: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("email")
    var email: Int,

    @SerializedName("password")
    var password: String,

    @SerializedName("phoneNumber")
    var phoneNumber: Int,

    @SerializedName("balance")
    var balance: String,

    @SerializedName("token")
    var token: String
)