package com.tuly.ordermanager.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tuly.ordermanager.models.Order


@Dao
interface OrdersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(order: Order): Long

    @Query("SELECT * FROM orders")
    fun getAllOrders(): LiveData<List<Order>>

    @Delete
    suspend fun deleteOrder(order: Order)
}