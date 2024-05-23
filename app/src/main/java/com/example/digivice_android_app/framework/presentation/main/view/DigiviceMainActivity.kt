package com.example.digivice_android_app.framework.presentation.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.digivice_android_app.R
import com.example.digivice_android_app.databinding.ActivityDigiviceMainBinding
import com.example.digivice_android_app.framework.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DigiviceMainActivity : AppCompatActivity() {

    private val binding by viewBinding {
        ActivityDigiviceMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initNavController()
    }

    private fun initNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            binding.digiviceFragmentContainer.id
        ) as NavHostFragment

        navHostFragment
            .navController
            .navInflater
            .inflate(R.navigation.nav_graph_digivice)
    }
}