package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName

class SendPhone(
    @SerializedName("telephoneNumber")
    var telephoneNumber: String?
)