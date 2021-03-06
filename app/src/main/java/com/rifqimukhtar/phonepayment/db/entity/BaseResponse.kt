package com.rifqimukhtar.phonepayment.db.entity

import androidx.annotation.Nullable
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class BaseResponse<T> {
    @SerializedName("data") @Nullable
    var data: T? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("status")
    var status: Int? = null
}