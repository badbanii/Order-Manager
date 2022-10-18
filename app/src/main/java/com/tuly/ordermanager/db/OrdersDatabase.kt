package com.tuly.ordermanager.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tuly.ordermanager.models.Order

@Database(
    entities = [Order::class],
    version = 1
)

abstract class OrdersDatabase : RoomDatabase() {
    abstract fun getOrderDao(): OrdersDao

    ///Make sure that only one instance exists (also only one thread can modify it)
    companion object {
        @Volatile
        private var instance: OrdersDatabase? = null
        private val LOCK = Any()

        ///if database doesn't exists it will be created
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK)
        {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                OrdersDatabase::class.java,
                "orders.db"
            ).build()
    }
}