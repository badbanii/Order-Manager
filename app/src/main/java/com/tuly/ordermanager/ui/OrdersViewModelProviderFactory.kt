package com.tuly.ordermanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tuly.ordermanager.repository.OrdersRepository

class OrdersViewModelProviderFactory(val ordersRepository: OrdersRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrdersViewModel(ordersRepository) as T
    }
}