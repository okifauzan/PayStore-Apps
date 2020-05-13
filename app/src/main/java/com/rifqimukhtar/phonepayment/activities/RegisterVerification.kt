package com.rifqimukhtar.phonepayment.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import com.rifqimukhtar.phonepayment.R
import kotlinx.android.synthetic.main.activity_register_verification.*

class RegisterVerification : AppCompatActivity() {

    val patternOTP = "^[0-9]{4}\$".toRegex()
    var postName: String? = null
    var postEmail: String? = null
    var postPhoneNumber: String? = null
    var postPassword: String? = null
    var getOTP: String? = null
    var timerAvailable: Boolean = true

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
            timerCount()
            btnResend.isEnabled = false
            btnResend.setTextColor(Color.RED)
            getOTP = "4321"
        }
        btnRegConfirmOTP.setOnClickListener {
            var textOTP = etRegOTPNumber.text.toString()
            if ((textOTP == getOTP) && timerAvailable){
                Log.d("test", "$postName, $postEmail, $postPhoneNumber, $postPassword")
                startActivity(Intent(this@RegisterVerification, LoginActivity::class.java))
            } else {
                Log.d("test", "Wrong OTP Number")
            }
        }
    }
}
