package com.rifqimukhtar.phonepayment.db.entity

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName

class BasePaymentResponse<T> {

    @SerializedName("message")
    var message: String? = null

    @SerializedName("status")
    var status: Int? = null
}