package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("token")
    var token: String,

    @SerializedName("message")
    var message: String
)