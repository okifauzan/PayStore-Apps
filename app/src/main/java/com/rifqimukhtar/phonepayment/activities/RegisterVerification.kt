package com.rifqimukhtar.phonepayment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rifqimukhtar.phonepayment.R
import kotlinx.android.synthetic.main.activity_register_verification.*

class RegisterVerification : AppCompatActivity() {

    val patternOTP = "^[0-9]{4}\$".toRegex()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_verification)
        onClickGroup()
    }

    fun onClickGroup() {
        btnRegConfirmOTP.setOnClickListener {
            startActivity(Intent(this@RegisterVerification, LoginActivity::class.java))
        }
    }
}
