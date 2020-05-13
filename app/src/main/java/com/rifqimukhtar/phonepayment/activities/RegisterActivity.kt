package com.rifqimukhtar.phonepayment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
            var textEmail = etRegistEmail.text.toString()
            var textHandphone = etRegistHandphone.text.toString()
            var textPassword = etRegistPassword.text.toString()
            var textRepeatPassword = etRegistRepeatPassword.text.toString()
            if (!(patternName.matches(textName))){
                Log.d("test", "Wrong Name Format")
            } else if (!(patternEmail.matches(textEmail))){
                Log.d("test", "Wrong Email Format")
            } else if (!(patternHandphone.matches(textHandphone))){
                Log.d("test", "Wrong Number Format")
            } else if (!(patternPassword.matches(textPassword))){
                Log.d("test", "Wrong Password Format")
            } else if (!(textRepeatPassword.equals(textPassword))){
                Log.d("test", "Password didn't matches")
            } else {
                val bundle = Bundle()
                bundle.putString("name", textName)
                bundle.putString("email", textEmail)
                bundle.putString("phoneNumber", textHandphone)
                bundle.putString("password", textPassword)
                bundle.putString("otp", "1234")
                val intent = Intent(this@RegisterActivity, RegisterVerification::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }

        btnRegisterBack.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
    }
}
