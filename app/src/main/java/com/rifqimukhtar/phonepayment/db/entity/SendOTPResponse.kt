package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName

data class SendOTPResponse (
    @SerializedName("phoneNumber")
    var phoneNumber: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("otp")
    var otp: String
)