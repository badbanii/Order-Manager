package com.tuly.ordermanager.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.tuly.ordermanager.R
import com.tuly.ordermanager.ui.OrdersActivity
import com.tuly.ordermanager.ui.OrdersViewModel
import kotlinx.android.synthetic.main.fragment_detailed_order.*

class DetailedOrderFragment : Fragment(R.layout.fragment_detailed_order) {

    lateinit var viewModel: OrdersViewModel
    val args: DetailedOrderFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as OrdersActivity).viewModel
        val order = args.order
        textview_detail_description.text = order.description
        textview_detail_owner.text = order.deliverTo
        textview_detail_price.text = order.price.toString()
        textview_detail_status.text = order.status
        textview_detail_id.text = order.id.toString()

        fab.setOnClickListener {
            viewModel.saveOrder(order)
            Snackbar.make(view, "Order saved", Snackbar.LENGTH_SHORT).show()
        }

        radiogroup_status.setOnCheckedChangeListener { radioGroup, optionId ->
            run {
                when (optionId) {
                    R.id.radiobutton_new -> {
                        textview_detail_status.text = getString(R.string.new_status)
                    }
                    R.id.radiobutton_pending -> {
                        textview_detail_status.text = getString(R.string.pending_status)
                    }
                    R.id.radiobutton_delivered -> {
                        textview_detail_status.text = getString(R.string.delivered_status)
                    }
                }
            }
        }

    }
}