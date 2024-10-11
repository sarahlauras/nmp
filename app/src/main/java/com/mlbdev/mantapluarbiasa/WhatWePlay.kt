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
    private lateinit var binding: ActivityWhatWePlayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWhatWePlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recGames.layoutManager = LinearLayoutManager(this)
        binding.recGames.setHasFixedSize(true)
        binding.recGames.adapter = GameAdapter()
    }
}