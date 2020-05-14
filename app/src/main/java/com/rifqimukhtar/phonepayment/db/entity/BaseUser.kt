package com.rifqimukhtar.phonepayment.db.entity

import com.google.gson.annotations.SerializedName

class BaseUser{
    @SerializedName("userProfile")
    var userProfile: User? = null

    constructor(userProfile: User?) {
        this.userProfile = userProfile
    }
}