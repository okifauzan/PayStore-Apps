package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("idUser")
    var idUser: Int,

    @SerializedName("token")
    var token: String,

    @SerializedName("message")
    var message: String,

    @SerializedName("status")
    var status: Int
)