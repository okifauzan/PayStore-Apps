package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName

data class SendOTPResponse (
    @SerializedName("status")
    var status: Int,

    @SerializedName("otp")
    var otp: String
)