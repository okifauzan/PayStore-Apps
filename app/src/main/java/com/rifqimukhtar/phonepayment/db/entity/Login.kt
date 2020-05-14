package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName

data class Login (
    @SerializedName("phoneNumber")
    var phoneNumber: String,

    @SerializedName("password")
    var password: String
)