package com.rifqimukhtar.phonepayment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rifqimukhtar.phonepayment.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val patternLoginNumber = "^[0-9]{9,12}\$".toRegex()
    val patternLoginPass = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%!_?&])(?=\\S+\$).{8,20}\$".toRegex()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        onClickGroup()
    }

    fun onClickGroup(){
        btnLogin.setOnClickListener {
            startActivity(Intent(this@LoginActivity, MainMenuActivity::class.java))
        }
        btnToRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }
}
