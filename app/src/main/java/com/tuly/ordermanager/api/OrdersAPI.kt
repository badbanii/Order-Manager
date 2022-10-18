package com.tuly.ordermanager.api

import com.tuly.ordermanager.models.OrdersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface OrdersAPI {

    @GET("/flower-manager-v1")
    suspend fun getNewOrders(
    ): Response<OrdersResponse>

    @PATCH("/flower-manager-v1")
    suspend fun updateOrder(
        @Path("id") id: Long,
        status: String
    ): Response<OrdersResponse>
}



