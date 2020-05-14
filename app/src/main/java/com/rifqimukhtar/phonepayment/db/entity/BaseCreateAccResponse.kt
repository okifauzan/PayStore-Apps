package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName

data class BaseCreateAccResponse<T> (
    @SerializedName("newUser")
    var newUser: T,

    @SerializedName("status")
    var status: String
)