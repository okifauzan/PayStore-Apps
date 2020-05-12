package com.rifqimukhtar.phonepayment.db.entity

import android.widget.ImageView
import com.google.gson.annotations.SerializedName

public class PaymentMethod{
    @SerializedName("client_id")
    var image: Int? = null

    @SerializedName("lat")
    var methodName: String? = null

    @SerializedName("lng")
    var methodValue: String? = null

    constructor(image: Int, methodName: String?, methodValue: String?) {
        this.image = image
        this.methodName = methodName
        this.methodValue = methodValue
    }
}