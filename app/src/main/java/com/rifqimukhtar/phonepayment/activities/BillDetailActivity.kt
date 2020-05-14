package com.rifqimukhtar.phonepayment.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.rifqimukhtar.phonepayment.R

class BillDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill_detail)


        if (savedInstanceState == null) {
            val billDetailFragment: Fragment = DetailBillFragment()
            val ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
            ft.add(R.id.billDetailFrame, billDetailFragment).commit()
            Log.d("State", "Fragment added")
        }
    }


}
