package com.rifqimukhtar.phonepayment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rifqimukhtar.phonepayment.R
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        ivCircleBottom.setOnClickListener {
            val preference = getSharedPreferences("Pref_Profile", 0)
            val editor = preference.edit()
            editor.putBoolean("PREF_ISLOGIN", false)
            editor.apply()
            startActivity(Intent(this@MainMenuActivity, LoginActivity::class.java))
            buttonGroup()
        }
    }

    private fun buttonGroup() {
        btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        btnTelkomOption.setOnClickListener {
            startActivity(Intent(this, TelkomPaymentActivity::class.java))
        }
    }
}
