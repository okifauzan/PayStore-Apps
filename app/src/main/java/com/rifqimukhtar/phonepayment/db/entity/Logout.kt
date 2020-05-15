package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName

data class Logout (
    @SerializedName("idUser")
    var idUser: String
)