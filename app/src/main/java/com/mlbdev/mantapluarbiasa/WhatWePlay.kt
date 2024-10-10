package com.mlbdev.mantapluarbiasa

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mlbdev.mantapluarbiasa.databinding.ActivityOurScheduleBinding
import com.mlbdev.mantapluarbiasa.databinding.ActivitySchedulePageDetailBinding
import com.mlbdev.mantapluarbiasa.databinding.ActivityWhatWePlayBinding

class WhatWePlay : AppCompatActivity() {
    private lateinit var binding: ActivityOurScheduleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOurScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.schedulePage.layoutManager = LinearLayoutManager(this)
        binding.schedulePage.setHasFixedSize(true)
        binding.schedulePage.adapter = OurScheduleAdapter()
    }
}