package com.rifqimukhtar.phonepayment.db.entity

import android.widget.ImageView
import com.google.gson.annotations.SerializedName

public class PaymentMethod{
    @SerializedName("image")
    var image: Int? = null

    @SerializedName("methodName")
    var methodName: String? = null

    @SerializedName("methodValue")
    var methodValue: String? = null

    @SerializedName("methodValue")
    var isEnoughBalance: Boolean? = null

    constructor(image: Int?, methodName: String?, methodValue: String?, isEnoughBalance: Boolean?) {
        this.image = image
        this.methodName = methodName
        this.methodValue = methodValue
        this.isEnoughBalance = isEnoughBalance
    }
}