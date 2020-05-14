package com.rifqimukhtar.phonepayment.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.db.entity.PaymentMethod
import com.rifqimukhtar.phonepayment.db.entity.PhoneBill
import com.rifqimukhtar.phonepayment.fragments.PaymentNotFound
import kotlinx.android.synthetic.main.activity_telkom_payment.*

class TelkomPaymentActivity : AppCompatActivity() {

    private var isEnoughBalance = true
    private var selectedMethod:PaymentMethod? = null

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
                getPhoneBill()
                startActivity(Intent(this, BillDetailActivity::class.java))
//                val dialogFragment = DetailBillFragment()
//                var ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
//                ft?.addToBackStack(null)
//                dialogFragment.show(ft!!, "dialog")
            }
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
            setSelectedMethod(virtualAcc)

            //setvalue for send in showPaymentMethod
            isEnoughBalance = false

        } else{
            //Enough balance, use wallet
            val methodValue = balance.toString()
            val eWallet = PaymentMethod(R.drawable.ic_wallet, "PayStore Wallet", methodValue, true)
            setSelectedMethod(eWallet)
            //setvalue for send in showPaymentMethod
            isEnoughBalance = true
        }
    }

    fun setSelectedMethod(method: PaymentMethod) {
        this.selectedMethod = method
    }
    fun getSelectedMethod(): PaymentMethod? {
        return selectedMethod
    }


    private fun showNotFoundDialog() {
        val dialogFragment = PaymentNotFound()
        var ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
        ft?.addToBackStack(null)
        dialogFragment.show(ft!!, "dialog")
    }


}
