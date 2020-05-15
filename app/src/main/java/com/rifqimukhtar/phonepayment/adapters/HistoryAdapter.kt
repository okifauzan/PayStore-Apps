package com.rifqimukhtar.phonepayment.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.db.entity.BillHistory

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
    private var listItem: List<BillHistory> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryAdapter.HistoryHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history_transaction, parent, false)
        return HistoryHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: HistoryAdapter.HistoryHolder, position: Int) {
        holder.bind(listItem[position])
    }
    inner class HistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: BillHistory) {
            Log.d("State", "Bind viewholder")
            val tvHistoryNominal= itemView.findViewById<TextView>(R.id.tvHistoryNominal)
            val tvHistoryTime = itemView.findViewById<TextView>(R.id.tvHistoryTime)
            val tvHistoryStatus = itemView.findViewById<TextView>(R.id.tvHistoryStatus)

            tvHistoryNominal.text = item.amount.toString()
            tvHistoryTime.text = item.timestamp
            //tvHistoryStatus.text = item.status.toString()
            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(item) }
        }
    }

    fun setItem(listItem: ArrayList<BillHistory>?){
        if (listItem != null) {
            Log.d("State", "Set Item")
            this.listItem = listItem
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: BillHistory)
    }

}