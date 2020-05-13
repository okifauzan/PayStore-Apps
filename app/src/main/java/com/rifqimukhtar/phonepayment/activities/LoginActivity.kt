package com.rifqimukhtar.phonepayment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
            var textLoginNumber = etLoginHandphone.text.toString()
            var textLoginPass = etLoginPassword.text.toString()
            if (patternLoginNumber.matches(textLoginNumber) && patternLoginPass.matches(textLoginPass)){
                val preference = getSharedPreferences("Pref_Profile", 0)
                val editor = preference.edit()
                editor.putBoolean("PREF_ISLOGIN", true)
                editor.apply()
                startActivity(Intent(this@LoginActivity, MainMenuActivity::class.java))
            } else {
                Toast.makeText(applicationContext, "Wrong Phone/Pass Format", Toast.LENGTH_SHORT).show()
            }
        }
        btnToRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }
}
