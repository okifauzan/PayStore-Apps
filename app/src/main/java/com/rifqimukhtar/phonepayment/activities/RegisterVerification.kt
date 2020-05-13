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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_verification)
        if (intent.extras != null){
            val bundle = intent.extras
            postName = bundle?.getString("name")
            postEmail = bundle?.getString("email")
            postPhoneNumber = bundle?.getString("phoneNumber")
            postPassword = bundle?.getString("password")
        }
        timerCount()
        onClickGroup()
    }

    fun timerCount(){
        val timer = object : CountDownTimer(30000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                tvTimerOTP.setText("Wait ${millisUntilFinished/1000} seconds to resend code")
                btnResend.isEnabled = false
                btnResend.setTextColor(Color.RED)
            }

            override fun onFinish() {
                btnResend.isEnabled = true
                btnResend.setTextColor(Color.BLUE)
            }
        }.start()
    }

    fun onClickGroup() {

        btnResend.setOnClickListener {
            timerCount()
            btnResend.isEnabled = false
            btnResend.setTextColor(Color.RED)
        }
        btnRegConfirmOTP.setOnClickListener {
            if (patternOTP.matches(etRegOTPNumber.text.toString())){
                Log.d("test", "$postName, $postEmail, $postPhoneNumber, $postPassword")
                startActivity(Intent(this@RegisterVerification, LoginActivity::class.java))
            } else {
                Log.d("test", "Wrong OTP Format")
            }
        }
    }
}
