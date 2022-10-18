package com.tuly.ordermanager.repository

import com.tuly.ordermanager.api.RetrofitInstance
import com.tuly.ordermanager.db.OrdersDatabase
import com.tuly.ordermanager.models.Order

//getting data from db and through RetroFit
class OrdersRepository(val db: OrdersDatabase) {
    suspend fun getNewOrders() =
        RetrofitInstance.api.getNewOrders()

    suspend fun upsert(order: Order) = db.getOrderDao().upsert(order)

    fun getSavedOrders() = db.getOrderDao().getAllOrders()

    suspend fun deleteOrder(order: Order) = db.getOrderDao().deleteOrder(order)

    suspend fun updateOrder(id: Long, status: String) = RetrofitInstance.api.updateOrder(id, status)
}