package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName

data class CreateAccountResponse (
    @SerializedName("idUser")
    var idUser: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("phoneNumber")
    var phoneNumber: String,

    @SerializedName("balance")
    var balance: Int,

    @SerializedName("token")
    var token: String
)