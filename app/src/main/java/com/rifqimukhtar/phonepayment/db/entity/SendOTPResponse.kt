package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName

data class SendOTPResponse (
    @SerializedName("phoneNumber")
    var phoneNumber: String? = null,

    @SerializedName("email")
    var email: String?= null,

    @SerializedName("otp")
    var otp: String?= null
)