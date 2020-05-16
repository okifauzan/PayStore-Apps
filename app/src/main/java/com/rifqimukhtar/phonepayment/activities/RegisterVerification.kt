package com.rifqimukhtar.phonepayment.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.db.entity.*
import com.rifqimukhtar.phonepayment.rest.ApiClient
import com.rifqimukhtar.phonepayment.rest.ApiInteface
import kotlinx.android.synthetic.main.activity_register_verification.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterVerification : AppCompatActivity() {

    val patternOTP = "^[0-9]{4}\$".toRegex()
    var postName: String? = null
    var postEmail: String? = null
    var postPhoneNumber: String? = null
    var postPassword: String? = null
    var getOTP: String? = null
    var timerAvailable: Boolean = true
    companion object val API_KEY = "xxxxxx"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_verification)
        if (intent.extras != null){
            val bundle = intent.extras
            postName = bundle?.getString("name")
            postEmail = bundle?.getString("email")
            postPhoneNumber = bundle?.getString("phoneNumber")
            postPassword = bundle?.getString("password")
            getOTP = bundle?.getString("otp")
            Log.d("bundle", getOTP)
        }
        timerCount()
        onClickGroup()
    }

    fun timerCount(){
        val timer = object : CountDownTimer(30000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                tvTimerOTP.setText("OTP valid for ${millisUntilFinished/1000} seconds")
                btnResend.isEnabled = false
                btnResend.setTextColor(Color.RED)
                timerAvailable = true
            }

            override fun onFinish() {
                btnResend.isEnabled = true
                btnResend.setTextColor(Color.BLUE)
                timerAvailable = false
            }
        }.start()
    }

    fun onClickGroup() {

        btnResend.setOnClickListener {

            val sendOtpModel = SendOTP("+6287883445469", postEmail)
            val sendOtpCall = ApiClient.getClient()?.create(ApiInteface::class.java)?.postOTP(sendOtpModel)
            sendOtpCall?.enqueue(object : Callback<SendOTPResponse>{
                override fun onResponse(call: Call<SendOTPResponse>, response: Response<SendOTPResponse>) {
                    timerCount()
                    btnResend.isEnabled = false
                    btnResend.setTextColor(Color.RED)
                    val otp = response.body()!!.otp
                    getOTP = otp
                    Log.d("otp", getOTP)
                    //TODO("Delete when done debugging")
                    Toast.makeText(applicationContext, getOTP.toString(), Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<SendOTPResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Failed to send OTP", Toast.LENGTH_SHORT).show()
                    Log.d("Failed", t.message)
                }
            })
        }
        btnRegConfirmOTP.setOnClickListener {
            var textOTP = etRegOTPNumber.text.toString()
            if (timerAvailable){
                if (textOTP.trim().isEmpty()){
                    Toast.makeText(applicationContext, "OTP cannot be empty", Toast.LENGTH_SHORT).show()
                } else {
                    if (textOTP == getOTP){
                        Log.d("test", "$postName, $postEmail, $postPhoneNumber, $postPassword")

                        val registerModel = CreateAccount(postName, postEmail, postPassword, postPhoneNumber)
                        val registerCall = ApiClient.getClient()?.create(ApiInteface::class.java)?.postRegister(registerModel)

                        registerCall?.enqueue(object : Callback<BaseResponse<CreateAccountResponse>>{
                            override fun onResponse(call: Call<BaseResponse<CreateAccountResponse>>,
                                                    response: Response<BaseResponse<CreateAccountResponse>>) {
                                if (response.isSuccessful){
                                    val message = response.body()!!.status
                                    Log.d("test", "$postName, $postEmail, $postPhoneNumber, $postPassword, = $message")
                                    startActivity(Intent(this@RegisterVerification, LoginActivity::class.java))
                                } else {
                                    Toast.makeText(applicationContext, "Can't Register", Toast.LENGTH_SHORT).show()
                                    Log.d("Response", response.toString())
                                }
                            }

                            override fun onFailure(call: Call<BaseResponse<CreateAccountResponse>>, t: Throwable) {
                                Toast.makeText(applicationContext, "Can't Response", Toast.LENGTH_SHORT).show()
                                Log.d("Failure", t.message)
                            }
                        })

                    } else {
                        Log.d("test", "Invalid OTP")
                    }
                }
            } else{
                Toast.makeText(applicationContext, "OTP Code Expired", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
