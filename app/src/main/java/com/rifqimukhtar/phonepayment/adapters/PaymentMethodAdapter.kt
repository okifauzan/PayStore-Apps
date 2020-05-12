package com.rifqimukhtar.phonepayment.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rifqimukhtar.phonepayment.R
import com.rifqimukhtar.phonepayment.db.entity.PaymentMethod

class PaymentMethodAdapter : RecyclerView.Adapter<PaymentMethodAdapter.MetodeBayarHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
    private var listItem: List<PaymentMethod> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PaymentMethodAdapter.MetodeBayarHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_metode_bayar, parent, false)
        return MetodeBayarHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: PaymentMethodAdapter.MetodeBayarHolder, position: Int) {
        holder.bind(listItem[position])
    }
    inner class MetodeBayarHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: PaymentMethod) {
            Log.d("State", "Bind viewholder")
            val ivMethod = itemView.findViewById<ImageView>(R.id.ivMethod)
            val tvTitleMethod= itemView.findViewById<TextView>(R.id.tvTitleMethod)
            val tvValueMethod = itemView.findViewById<TextView>(R.id.tvValueMethod)

            ivMethod.setImageResource(item.image!!)
            tvTitleMethod.text = item.methodName
            tvValueMethod.text = item.methodValue
            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(item) }
        }
    }

    fun setItem(listItem: ArrayList<PaymentMethod>?){
        if (listItem != null) {
            Log.d("State", "Set Item")
            this.listItem = listItem
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: PaymentMethod)
    }
}