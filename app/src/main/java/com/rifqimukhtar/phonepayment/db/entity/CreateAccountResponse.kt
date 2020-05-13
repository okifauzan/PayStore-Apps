package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName

data class CreateAccountResponse (
    @SerializedName("status")
    var status: Int,

    @SerializedName("message")
    var message: String
)