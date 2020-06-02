package com.bhakti.personalcovidtracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.bhakti.personalcovidtracker.R
import com.bhakti.personalcovidtracker.viewmodel.OverviewViewModel
import com.bhakti.personalcovidtracker.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Main launcher activity to display  COVID19 stats
 * Shares viewmodel across fragments hosted
 * Supports navigationController
 *
 * @author Bhakti
 */
class CovidStatsActivity : AppCompatActivity() {
    private val viewModel: OverviewViewModel by lazy { ViewModelProvider(this, ViewModelFactory()).get(OverviewViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(navView, navController)
        viewModel.fetchSummaryStats()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}
