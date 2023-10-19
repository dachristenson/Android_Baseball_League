package com.example.abl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.abl.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.containerFragment) as NavHostFragment

        this.navController = navHostFragment.navController
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.navView.setupWithNavController(this.navController)
        this.appBarConfiguration =
            AppBarConfiguration(binding.navView.menu, binding.drawerLayout)
        setupActionBarWithNavController(this.navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean =
        (this::navController.isInitialized &&
                this.navController.navigateUp(this.appBarConfiguration)
                ) || super.onSupportNavigateUp()
}