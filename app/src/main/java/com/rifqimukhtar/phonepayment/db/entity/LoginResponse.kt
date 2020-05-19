package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("idUser")
    var idUser: Int? = null,

    @SerializedName("token")
    var token: String? = null,

    @SerializedName("message")
    var message: String? =null,

    @SerializedName("status")
    var status: Int? = null
)