package com.rifqimukhtar.phonepayment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.db.entity.PaymentMethod
import com.rifqimukhtar.phonepayment.db.entity.PhoneBill
import com.rifqimukhtar.phonepayment.fragments.PaymentMethodFragment
import com.rifqimukhtar.phonepayment.fragments.PaymentNotFound
import com.rifqimukhtar.phonepayment.fragments.PaymentResultFragment
import kotlinx.android.synthetic.main.activity_telkom_payment.*
import kotlinx.android.synthetic.main.item_metode_bayar.*

class TelkomPaymentActivity : AppCompatActivity() {

    private var isEnoughBalance = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_telkom_payment)
        buttonGroup()
    }

    private fun buttonGroup() {
        btnCekTagihan.setOnClickListener{
            if (etNomorTelepon.text.toString().trim().isEmpty())
            {
                showNotFoundDialog()
            } else
            {
                linearLayoutDetailTagihan.visibility = View.VISIBLE
                btnCekTagihan.visibility = View.GONE
                getPhoneBill()
            }
        }

        btnMetodeOption.setOnClickListener {
            showPaymentMethod()
        }

        btnBayarTagihan.setOnClickListener {
            linearLayoutDetailTagihan.visibility = View.GONE
            btnCekTagihan.visibility = View.VISIBLE

           // TODO("call bayar tagihan api")
            etNomorTelepon.text?.clear()
            showSuccessDialog()
        }

        ibBackFromTagihan.setOnClickListener {
            startActivity(Intent(this, MainMenuActivity::class.java))
        }
    }

    private fun getPhoneBill() {
        val phoneNumber = etNomorTelepon.text.toString()

        //TODO("request API get user balance & get phone bill")

        val phoneBill = PhoneBill(1,phoneNumber,"Owner",2500)
        checkWalletBalance(phoneBill)
        //TODO("set bill detail sesuai API")
        setBillDetail()
    }

    private fun setBillDetail() {
        //TODO("Not yet implemented")
    }

    private fun checkWalletBalance(phoneBill: PhoneBill) {
        //TODO("get real balance value from user")
        val balance = 200000

        if (balance <= phoneBill.amount!!)
        {
            //Not enough balance, use Virtual Acc

            //TODO("add real user virtual number")
            val virtualNumber = "${phoneBill.telephoneNumber}0123456"
            val virtualAcc = PaymentMethod(R.drawable.ic_virtual_acc, "Virtual Account",virtualNumber, true)
            updateSelectedMethod(virtualAcc)

            //setvalue for send in showPaymentMethod
            isEnoughBalance = false

        } else{
            //Enough balance, use wallet
            val methodValue = balance.toString()
            val eWallet = PaymentMethod(R.drawable.ic_wallet, "PayStore Wallet", methodValue, true)
            updateSelectedMethod(eWallet)
            //setvalue for send in showPaymentMethod
            isEnoughBalance = true
        }
    }

    fun updateSelectedMethod(method: PaymentMethod) {
        ivSelectedMethod.setImageResource(method.image!!)
        tvSelectedTitleMethod.text = method.methodName
        //TODO("add real user balance")
        tvSelectedValueMethod.text = method.methodValue
    }


    private fun showPaymentMethod() {
        val dialogFragment = PaymentMethodFragment()
        val bundle = Bundle()
        bundle.putBoolean("isBalanceEnough", isEnoughBalance)
        dialogFragment.arguments = bundle

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

    private fun showSuccessDialog() {
        val dialogFragment = PaymentResultFragment()
        var ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
        ft?.addToBackStack(null)
        dialogFragment.show(ft!!, "dialog")
    }
}
