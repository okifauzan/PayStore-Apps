package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class BaseResponse<T> {
    @SerializedName("data")
    var data: T? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("status")
    var status: Int? = null
}