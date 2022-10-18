package com.tuly.ordermanager.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuly.ordermanager.R
import com.tuly.ordermanager.adapters.OrderAdapter
import com.tuly.ordermanager.ui.OrdersActivity
import com.tuly.ordermanager.ui.OrdersViewModel
import com.tuly.ordermanager.util.Resource
import kotlinx.android.synthetic.main.fragment_new_orders.*

class NewOrdersFragment : Fragment(R.layout.fragment_new_orders) {

    lateinit var viewModel: OrdersViewModel
    lateinit var orderAdapter: OrderAdapter
    val TAG = "NewOrdersFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as OrdersActivity).viewModel
        setupRecycleView()

        orderAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("order", it)
            }
            findNavController().navigate(
                R.id.action_newOrdersFragment_to_detailedOrderFragment,
                bundle
            )
        }

        viewModel.newOrders.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { ordersResponse ->
                        orderAdapter.differ.submitList(ordersResponse.orders.toList())
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message -> Log.e(TAG, "Error: $message") }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        progressbar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        progressbar.visibility = View.VISIBLE
        isLoading = true
    }

    var isLoading = false

    private fun setupRecycleView() {
        orderAdapter = OrderAdapter()
        recycleview_new_orders.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}