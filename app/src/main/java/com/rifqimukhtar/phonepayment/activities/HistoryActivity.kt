package com.rifqimukhtar.phonepayment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.adapters.HistoryAdapter
import com.rifqimukhtar.phonepayment.adapters.PaymentMethodAdapter
import com.rifqimukhtar.phonepayment.db.entity.BillHistory
import com.rifqimukhtar.phonepayment.db.entity.PaymentMethod
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.fragment_payment_method.*

class HistoryActivity : AppCompatActivity() {

    var listBillHistory: ArrayList<BillHistory> = ArrayList()
    private lateinit var adapter: HistoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        adapter = HistoryAdapter()
        adapter.notifyDataSetChanged()

        generateBillHistory()
        setupRecyclerView()
        buttonGroup()
    }

    private fun buttonGroup() {
        ibBackFromHistory.setOnClickListener {
            startActivity(Intent(this, MainMenuActivity::class.java))
        }
    }

    private fun generateBillHistory() {
        val bill1 = BillHistory(1, 75000, 1, 1, "24/5/2020" )
        val bill2 = BillHistory(3, 125000, 1, 1, "28/5/2020" )
        val bill3 = BillHistory(4, 25000, 1, 1, "20/5/2020" )
        val bill4 = BillHistory(5, 15000, 1, 1, "20/5/2020" )

        listBillHistory.add(bill1)
        listBillHistory.add(bill2)
        listBillHistory.add(bill3)
        listBillHistory.add(bill4)
        for (i in 0..15)
        {
            val bill = BillHistory(1, 75000, 1, 1, "24/5/2020" )
            listBillHistory.add(bill)
        }

    }


    private fun setupRecyclerView() {

        adapter.setItem(listBillHistory)
        adapter.setOnItemClickCallback(object : HistoryAdapter.OnItemClickCallback{
            override fun onItemClicked(data: BillHistory) {
                selectBill(data)
            }
        })

        rvHistory.layoutManager = LinearLayoutManager(this)
        rvHistory.setHasFixedSize(true)
        rvHistory.adapter = adapter
    }

    private fun selectBill(data: BillHistory) {
        Toast.makeText(this, data.amount.toString(),Toast.LENGTH_SHORT).show()
    }
}
