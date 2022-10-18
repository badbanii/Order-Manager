package com.tuly.ordermanager.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tuly.ordermanager.R
import com.tuly.ordermanager.adapters.OrderAdapter
import com.tuly.ordermanager.ui.OrdersActivity
import com.tuly.ordermanager.ui.OrdersViewModel
import kotlinx.android.synthetic.main.fragment_saved_orders.*

class SavedOrdersFragment : Fragment(R.layout.fragment_saved_orders) {

    lateinit var viewModel: ViewModel
    lateinit var orderAdapter: OrderAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as OrdersActivity).viewModel
        setupRecycleView()

        orderAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("order", it)
            }
            findNavController().navigate(
                R.id.action_savedOrdersFragment_to_detailedOrderFragment,
                bundle
            )
        }
        val itemTouchHelperPerCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val order = orderAdapter.differ.currentList[position]
                (viewModel as OrdersViewModel).deleteOrder(order)
                Snackbar.make(view, "Order Deleted", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        (viewModel as OrdersViewModel).saveOrder(order)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperPerCallback).apply {
            attachToRecyclerView(recycleview_saved_orders)
        }

        (viewModel as OrdersViewModel).getSavedOrders()
            .observe(viewLifecycleOwner, Observer { orders ->
                orderAdapter.differ.submitList(orders)
            })
    }

    private fun setupRecycleView() {
        orderAdapter = OrderAdapter()
        recycleview_saved_orders.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}