package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName

data class SendOTP (
    @SerializedName("phoneNumber")
    var phoneNumber: String? = null,

    @SerializedName("email")
    var email: String? = null
)