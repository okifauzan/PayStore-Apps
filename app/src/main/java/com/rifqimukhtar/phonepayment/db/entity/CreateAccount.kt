package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName

data class CreateAccount (
    @SerializedName ("name")
    var name: String,

    @SerializedName("phoneNumber")
    var phoneNumber: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("password")
    var password: String
)