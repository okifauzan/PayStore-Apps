package com.rifqimukhtar.phonepayment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.rifqimukhtar.phonepayment.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    val patternName = "^[a-zA-Z]{3,20}\$".toRegex()
    val patternEmail = "^[a-z]+([.-]?[a-z]+)*@[a-z]+([.-]?[a-z]+)*(\\.[a-z]{2,3})+\$".toRegex() //recipient name + @ + domain + . + top level domain
    val patternHandphone = "^[0-9]{9,12}\$".toRegex()
    val patternPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%!_?&])(?=\\S+\$).{8,20}\$".toRegex()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        onClickGroup()
    }

    fun onClickGroup(){
        btnRegister.setOnClickListener {
            var textName = etRegistNama.text.toString()
            if (patternName.matches(textName)) {
                Log.d("name", "valid")
            } else {
                Log.d("name", "invalid")
            }
            var textEmail = etRegistEmail.text.toString()
            if (patternEmail.matches(textEmail)) {
                Log.d("email", "valid")
            } else {
                Log.d("email", "invalid")
            }
            var textHandphone = etRegistHandphone.text.toString()
            if (patternHandphone.matches(textHandphone)) {
                Log.d("Handphone", "valid")
            } else {
                Log.d("Handphone", "invalid")
            }
            var textPassword = etRegistPassword.text.toString()
            if (patternPassword.matches(textPassword)) {
                Log.d("pass", "valid")
            } else {
                Log.d("pass", "invalid")
            }
            var textRepeatPassword = etRegistRepeatPassword.text.toString()
            if (textRepeatPassword.equals(textPassword)){
                Log.d("passRepeat", "valid")
            } else {
                Log.d("passRepeat", "invalid")
            }
        }
            //startActivity(Intent(this@RegisterActivity, RegisterVerification::class.java))
        btnRegisterBack.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
    }
}
