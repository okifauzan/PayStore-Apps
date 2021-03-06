package com.rifqimukhtar.phonepayment.db.entity

import android.widget.ImageView
import com.google.gson.annotations.SerializedName
import java.io.Serializable

public class PaymentMethod : Serializable{
    @SerializedName("image")
    var image: Int? = null

    @SerializedName("methodName")
    var methodName: String? = null

    @SerializedName("methodValue")
    var methodValue: String? = null

    @SerializedName("idPaymentMethod")
    var idPaymentMethod: Int? = null

    constructor(image: Int?, methodName: String?, methodValue: String?, idPaymentMethod: Int?) {
        this.image = image
        this.methodName = methodName
        this.methodValue = methodValue
        this.idPaymentMethod = idPaymentMethod
    }
}