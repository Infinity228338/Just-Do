package com.domore.justdo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.domore.justdo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        binding.bottomNavigationView.itemIconTintList = null
        setContentView(view)
    }
}