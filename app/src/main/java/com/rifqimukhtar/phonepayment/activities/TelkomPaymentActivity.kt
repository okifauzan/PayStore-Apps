package com.rifqimukhtar.phonepayment.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rifqimukhtar.phonepayment.R
import kotlinx.android.synthetic.main.activity_telkom_payment.*

class TelkomPaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_telkom_payment)
        buttonGroup()
    }

    private fun buttonGroup() {
        btnCekTagihan.setOnClickListener{
            linearLayoutDetailTagihan.visibility = View.VISIBLE
            btnCekTagihan.visibility = View.GONE
        }

        btnBayarTagihan.setOnClickListener {
            linearLayoutDetailTagihan.visibility = View.GONE
            btnCekTagihan.visibility = View.VISIBLE
        }
    }
}
