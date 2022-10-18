package com.tuly.ordermanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tuly.ordermanager.databinding.ItemOrderPreviewBinding
import com.tuly.ordermanager.models.Order

class OrderAdapter :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    inner class OrderViewHolder(val binding: ItemOrderPreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            binding.order = order
        }
    }
    
    ///no RecyclerViewAdapter
    ///DiffUtil because it doesnt refresh every item when some are changed and it works in background.

    private val differCallback = object : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val orderBinding = ItemOrderPreviewBinding.inflate(inflater, parent, false)
        return OrderViewHolder(orderBinding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = differ.currentList[position]

        holder.bind(differ.currentList[position])
        holder.itemView.apply {
            setOnClickListener {
                onIitemClickListener?.let { it(order) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onIitemClickListener: ((Order) -> Unit)? = null

    fun setOnItemClickListener(listener: (Order) -> Unit) {
        onIitemClickListener = listener
    }
}
