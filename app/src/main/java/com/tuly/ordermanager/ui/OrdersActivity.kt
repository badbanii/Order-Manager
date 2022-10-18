package com.tuly.ordermanager.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.tuly.ordermanager.R
import com.tuly.ordermanager.db.OrdersDatabase
import com.tuly.ordermanager.repository.OrdersRepository
import kotlinx.android.synthetic.main.activity_orders.*

class OrdersActivity : AppCompatActivity() {

    lateinit var viewModel: OrdersViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        forceOrientation()
        initializeVars()
        setBottomNavView()
    }

    private fun forceOrientation() {
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun initializeVars() {
        val ordersRepository = OrdersRepository(OrdersDatabase(this))
        val viewModelProviderFactory = OrdersViewModelProviderFactory(ordersRepository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(OrdersViewModel::class.java)
    }

    private fun setBottomNavView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navhostfragment_orders) as NavHostFragment
        val navController = navHostFragment.navController
        bottomnavigationview.setupWithNavController(navController)
    }
}
