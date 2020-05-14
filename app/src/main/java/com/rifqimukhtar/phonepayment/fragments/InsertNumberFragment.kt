package com.rifqimukhtar.phonepayment.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction

import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.activities.MainMenuActivity
import com.rifqimukhtar.phonepayment.activities.TelkomPaymentActivity
import com.rifqimukhtar.phonepayment.db.entity.PaymentMethod
import com.rifqimukhtar.phonepayment.db.entity.PhoneBill
import kotlinx.android.synthetic.main.fragment_insert_number.*

/**
 * A simple [Fragment] subclass.
 */
class InsertNumberFragment : Fragment() {

    private var isEnoughBalance = true
    private var selectedMethod:PaymentMethod? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insert_number, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                (activity as TelkomPaymentActivity).showDetailBillFragment(selectedMethod!!)
            }
        }

        ibBackFromTagihan.setOnClickListener {
            startActivity(Intent(activity, MainMenuActivity::class.java))
        }
    }

    private fun getPhoneBill() {
        val phoneNumber = etNomorTelepon.text.toString()

        //TODO("request API get user balance & get phone bill")

        val phoneBill = PhoneBill(1,phoneNumber,"Owner",2500000)
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
            val virtualAcc = PaymentMethod(R.drawable.ic_virtual_acc, "Virtual Account",virtualNumber, false)
            setActivitySelectedMethod(virtualAcc)

            //setvalue for send in showPaymentMethod
            isEnoughBalance = false

        } else{
            //Enough balance, use wallet
            val methodValue = balance.toString()
            val eWallet = PaymentMethod(R.drawable.ic_wallet, "PayStore Wallet", methodValue, true)
            setActivitySelectedMethod(eWallet)
            //setvalue for send in showPaymentMethod
            isEnoughBalance = true
        }
    }

    private fun setActivitySelectedMethod(method: PaymentMethod) {
        selectedMethod = method
        (activity as TelkomPaymentActivity).setSelectedMethod(method)
    }


    private fun showNotFoundDialog() {
        val dialogFragment = PaymentNotFound()
        var ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        ft?.addToBackStack(null)
        dialogFragment.show(ft!!, "dialog")
    }

}
