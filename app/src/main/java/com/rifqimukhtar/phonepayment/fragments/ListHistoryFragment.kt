package com.rifqimukhtar.phonepayment.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.activities.HistoryActivity
import com.rifqimukhtar.phonepayment.activities.MainMenuActivity
import com.rifqimukhtar.phonepayment.activities.TelkomPaymentActivity
import com.rifqimukhtar.phonepayment.adapters.HistoryAdapter
import com.rifqimukhtar.phonepayment.db.entity.BillHistory
import com.rifqimukhtar.phonepayment.db.entity.PaymentMethod
import kotlinx.android.synthetic.main.fragment_list_history.*

/**
 * A simple [Fragment] subclass.
 */
class ListHistoryFragment : Fragment() {
    var listBillHistory: ArrayList<BillHistory> = ArrayList()
    private lateinit var adapter: HistoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = HistoryAdapter()
        adapter.notifyDataSetChanged()

        generateBillHistory()
        setupRecyclerView()
        buttonGroup()
    }


    private fun buttonGroup() {
        ibBackFromHistory.setOnClickListener {
            startActivity(Intent(activity, MainMenuActivity::class.java))
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

        rvHistory.layoutManager = LinearLayoutManager(activity)
        rvHistory.setHasFixedSize(true)
        rvHistory.adapter = adapter
    }

    private fun selectBill(data: BillHistory) {
        Toast.makeText(activity, data.amount.toString(), Toast.LENGTH_SHORT).show()
        (activity as HistoryActivity).showDetailHistoryFragment(data)
    }
}
