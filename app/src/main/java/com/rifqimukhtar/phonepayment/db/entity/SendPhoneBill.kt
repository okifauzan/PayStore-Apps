package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName

class SendPhoneBill(
    @SerializedName("billCode")
    var billCode: String?,

    @SerializedName("paymentType")
    var paymentType: Int?
)