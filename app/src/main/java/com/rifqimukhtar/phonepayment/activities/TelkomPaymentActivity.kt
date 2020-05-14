package com.rifqimukhtar.phonepayment.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.db.entity.PaymentMethod
import com.rifqimukhtar.phonepayment.db.entity.PhoneBill
import com.rifqimukhtar.phonepayment.fragments.DetailBillFragment
import com.rifqimukhtar.phonepayment.fragments.InsertNumberFragment

class TelkomPaymentActivity : AppCompatActivity() {

    private var selectedMethod:PaymentMethod? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_telkom_payment)

        showInsertNumberFragment()
    }

    fun showInsertNumberFragment() {
        val insertNumberFragment: Fragment = InsertNumberFragment()
        val ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
        ft.replace(R.id.frameTelkomPayment, insertNumberFragment).commit()
        Log.d("State", "Fragment added")
    }

    fun showDetailBillFragment(bill: PhoneBill, method: PaymentMethod){
        val detailBillFragment: Fragment = DetailBillFragment()
        val bundle = Bundle()
        //send selected method for current Bill
        bundle.putSerializable("selectedMethod", method)
        bundle.putSerializable("currentBill", bill)
        detailBillFragment.arguments = bundle
        val ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
        ft.replace(R.id.frameTelkomPayment, detailBillFragment).commit()
        Log.d("State", "Fragment added")
    }

    fun setSelectedMethod(method:PaymentMethod){
        this.selectedMethod = method
    }

    fun getSelectedMethod() : PaymentMethod? {
        return selectedMethod
    }
}
