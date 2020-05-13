package com.rifqimukhtar.phonepayment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.db.entity.PaymentMethod
import com.rifqimukhtar.phonepayment.fragments.PaymentMethodFragment
import com.rifqimukhtar.phonepayment.fragments.PaymentNotFound
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

            if (!etNomorTelepon.text.toString().trim().isNotEmpty())
            {
                showNotFoundDialog()
            }
        }

        btnBayarTagihan.setOnClickListener {
            linearLayoutDetailTagihan.visibility = View.GONE
            btnCekTagihan.visibility = View.VISIBLE
            showPaymentMethod()
        }

        ibBackFromTagihan.setOnClickListener {
            startActivity(Intent(this, MainMenuActivity::class.java))
        }
    }

    private fun showPaymentMethod() {
        val dialogFragment = PaymentMethodFragment()
        var ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
        ft?.addToBackStack(null)
        dialogFragment.show(ft!!, "dialog")
    }

    private fun showNotFoundDialog() {
        val dialogFragment = PaymentNotFound()
        var ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
        ft?.addToBackStack(null)
        dialogFragment.show(ft!!, "dialog")
    }
}
