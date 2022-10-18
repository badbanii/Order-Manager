package com.tuly.ordermanager.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity(
    tableName = "orders"
)

data class Order(
    @PrimaryKey
    val id: Int?,

    @SerialName("deliver_to")
    val deliverTo: String? = "",

    val description: String? = "",

    val price: Int?,

    val status:String?
) : java.io.Serializable