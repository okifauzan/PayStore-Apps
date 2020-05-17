package com.rifqimukhtar.phonepayment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.adapters.HistoryAdapter
import com.rifqimukhtar.phonepayment.adapters.PaymentMethodAdapter
import com.rifqimukhtar.phonepayment.db.entity.BillHistory
import com.rifqimukhtar.phonepayment.db.entity.PaymentMethod
import com.rifqimukhtar.phonepayment.fragments.DetailBillFragment
import com.rifqimukhtar.phonepayment.fragments.DetailHistoryFragment
import com.rifqimukhtar.phonepayment.fragments.InsertNumberFragment
import com.rifqimukhtar.phonepayment.fragments.ListHistoryFragment
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.fragment_payment_method.*

class HistoryActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        showListHitoryFragment()
    }

    fun showListHitoryFragment() {
        val listHistoryFragment: Fragment = ListHistoryFragment()
        val ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
        ft.replace(R.id.frameHistory, listHistoryFragment).commit()
        Log.d("State", "Fragment added")
    }

    fun showDetailHistoryFragment(bill:BillHistory) {
        val detailHistoryFragment: Fragment = DetailHistoryFragment()
        val bundle = Bundle()
        //send selected method for current Bill
        bundle.putSerializable("selectedMethod", bill)
        detailHistoryFragment.arguments = bundle
        val ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
        ft.replace(R.id.frameHistory, detailHistoryFragment, "detailHistoryFragment").addToBackStack("detailHIstoryFragment").commit()
        Log.d("State", "Fragment added")
    }
//
//    fun showCamera() {
//        val cameraFragment: Fragment = CameraFragment()
//        val ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
//        ft.add(R.id.frameHistory, cameraFragment).commit()
//
//        Log.d("State", "Insert Fragment added")
//    }
}
