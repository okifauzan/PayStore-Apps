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
    fun getUser(@Body idUser:SendUser): Call<BaseResponse<User>>?

    @POST("payment/history")
    fun getHistory(@Body userHistory: SendUser): Call<BaseResponse<List<BillHistory>>>

    @POST("user/logout")
    fun postLogout(@Body logout: Logout): Call<LogoutResponse>

//    @POST
//    fun payTelephoneBill(@Body phoneBill: SendPhoneBill): Call<>
    @POST("user")
    fun postRegister(@Body createAccount: CreateAccount): Call<BaseResponse<CreateAccountResponse>>

    @POST("bill/telephone/unpaid")
    fun getPaymentDetail(@Body sendPhone: SendPhone): Call<BaseResponse<PhoneBill>>?

    @POST("payment")
    fun sendRequestPayment(@Body sendRequestPayment: SendRequestPayment): Call<BaseResponse<Any>>?

    @POST("payment/verify")
    fun verifyRequest(@Body sendRequestPayment: SendRequestPayment): Call<BaseResponse<Any>>?
}