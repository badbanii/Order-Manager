package com.tuly.ordermanager.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuly.ordermanager.models.Order
import com.tuly.ordermanager.models.OrdersResponse
import com.tuly.ordermanager.repository.OrdersRepository
import com.tuly.ordermanager.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class OrdersViewModel(
    val ordersRepository: OrdersRepository
) : ViewModel() {

    val newOrders: MutableLiveData<Resource<OrdersResponse>> = MutableLiveData()
    var newOrdersResponse: OrdersResponse? = null

    init {
        getNewOrders()
    }

    fun getNewOrders() = viewModelScope.launch {
        newOrders.postValue(Resource.Loading())
        val response = ordersRepository.getNewOrders()
        newOrders.postValue(handleNewOrdersResponse(response))
    }

    fun updateOrder() = viewModelScope.launch {
        ///???
    }

    private fun handleNewOrdersResponse(response: Response<OrdersResponse>): Resource<OrdersResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (newOrdersResponse == null) {
                    newOrdersResponse = resultResponse
                } else {
                    val oldOrders = newOrdersResponse?.orders
                    val newOrders = resultResponse.orders
                    oldOrders?.addAll(newOrders)
                }
                return Resource.Success(newOrdersResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveOrder(order: Order) = viewModelScope.launch {
        ordersRepository.upsert(order)
    }

    fun getSavedOrders() = ordersRepository.getSavedOrders()

    fun deleteOrder(order: Order) = viewModelScope.launch {
        ordersRepository.deleteOrder(order)
    }


}