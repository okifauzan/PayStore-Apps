package com.rifqimukhtar.phonepayment.rest

import com.rifqimukhtar.phonepayment.db.entity.*
import retrofit2.Call
import retrofit2.http.*

interface ApiInteface {
    @POST("user/login")
    fun postLogin(@Body login: Login): Call<LoginResponse>

    @POST("user/otp")
    fun postOTP(@Body otp: SendOTP): Call<SendOTPResponse>

    @GET("telephone_bill")
    fun getTelephoneBill(@Body phone: SendPhone): Call<PhoneBill>

    @POST("user/getProfile")
//    @HTTP(method = "GET", path = "user", hasBody = true)
    fun getUser(@Body idUser:SendUser): Call<BaseUser>?

//    @POST
//    fun payTelephoneBill(@Body phoneBill: SendPhoneBill): Call<>
    @POST("user")
    fun postRegister(@Body createAccount: CreateAccount): Call<BaseCreateAccResponse<CreateAccountResponse>>
}