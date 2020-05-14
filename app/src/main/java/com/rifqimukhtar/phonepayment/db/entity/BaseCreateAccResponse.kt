package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName

data class BaseCreateAccResponse<T> (
    @SerializedName("newUser")
    var status: T,

    @SerializedName("status")
    var message: String
)