package com.rifqimukhtar.phonepayment.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.db.entity.PaymentMethod
import com.rifqimukhtar.phonepayment.fragments.DetailBillFragment
import com.rifqimukhtar.phonepayment.fragments.InsertNumberFragment

class TelkomPaymentActivity : AppCompatActivity() {

    private var selectedMethod:PaymentMethod? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_telkom_payment)

        addInsertNumberFragment()
    }

    fun addInsertNumberFragment() {
        val insertNumberFragment: Fragment = InsertNumberFragment()
        val ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
        ft.replace(R.id.frameTelkomPayment, insertNumberFragment).commit()
        Log.d("State", "Fragment added")
    }

    fun addDetailBillFragment(){
        val detailBillFragment: Fragment =
            DetailBillFragment()
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
