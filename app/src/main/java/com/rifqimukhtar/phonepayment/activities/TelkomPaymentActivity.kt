package com.rifqimukhtar.phonepayment.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.db.entity.PaymentMethod
import com.rifqimukhtar.phonepayment.db.entity.PhoneBill
import com.rifqimukhtar.phonepayment.db.entity.SendUser
import com.rifqimukhtar.phonepayment.db.entity.User
import com.rifqimukhtar.phonepayment.fragments.DetailBillFragment
import com.rifqimukhtar.phonepayment.fragments.InsertNumberFragment
import com.rifqimukhtar.phonepayment.viewmodel.UserViewModel
import org.koin.android.ext.android.inject

class TelkomPaymentActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by inject()

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
        Log.d("State", "Insert Fragment added")
    }

    fun showDetailBillFragment(
        bill: PhoneBill,
        method: PaymentMethod,
        balance: Int
    ){
        val detailBillFragment: Fragment = DetailBillFragment()
        val bundle = Bundle()
        //send selected method for current Bill
        bundle.putSerializable("selectedMethod", method)
        bundle.putSerializable("currentBill", bill)
        bundle.putInt("balance", balance)
        detailBillFragment.arguments = bundle
        val ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
        ft.replace(R.id.frameTelkomPayment, detailBillFragment).addToBackStack("detailBillFragment").commit()
        Log.d("State", "Detail Fragment added. mehotd $method")
    }

    fun setSelectedMethod(method:PaymentMethod){
        this.selectedMethod = method
    }

    fun getSelectedMethod() : PaymentMethod? {
        return selectedMethod
    }

    fun getCurrentUser(userID: SendUser) : User? {
        var user:User? = null
        userViewModel.getUser(userID).observe(this, Observer<User> {
             user = it
        })
        return user
    }
}
