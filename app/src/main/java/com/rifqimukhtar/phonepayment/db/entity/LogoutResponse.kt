package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @SerializedName("status")
    var status: Int,

    @SerializedName("message")
    var message: String
)