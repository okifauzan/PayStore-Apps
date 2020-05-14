package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName

data class CreateAccount (
    @SerializedName ("name")
    var name: String?,

    @SerializedName("email")
    var phoneNumber: String?,

    @SerializedName("password")
    var email: String?,

    @SerializedName("phoneNumber")
    var password: String?
)