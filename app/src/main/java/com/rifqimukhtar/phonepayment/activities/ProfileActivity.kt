package com.rifqimukhtar.phonepayment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.db.entity.User
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        if (intent.extras != null) {
            val bundle = intent.extras
            val user = bundle?.getSerializable("currentUser") as User
            user.phoneNumber
            setUserDetail(user)
        }

        buttonGroup()
    }

    private fun setUserDetail(user:User) {
        tvUserName.text = user.name
        tvUserEmail.text = user.email
        tvUserPhone.text = user.phoneNumber
        tvUserValueWallet.text = user.balance.toString()
    }

    private fun buttonGroup() {
        ibBackFromProfil.setOnClickListener {
            startActivity(Intent(this, MainMenuActivity::class.java))
        }
        btnLogout.setOnClickListener {
            val preference = getSharedPreferences("Pref_Profile", 0)
            val editor = preference.edit()
            editor.putBoolean("PREF_ISLOGIN", false)
            editor.apply()
            startActivity(Intent(this, LoginActivity::class.java))

        }
    }
}
