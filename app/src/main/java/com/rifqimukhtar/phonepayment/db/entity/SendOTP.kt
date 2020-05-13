package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName

data class SendOTP (
    @SerializedName("phoneNumber")
    var phoneNumber: String,

    @SerializedName("email")
    var email: String
)