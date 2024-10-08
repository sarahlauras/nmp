package com.mlbdev.mantapluarbiasa

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mlbdev.mantapluarbiasa.databinding.ActivityMainBinding
import com.mlbdev.mantapluarbiasa.databinding.ActivitySchedulePageDetailBinding

class SchedulePageDetail : AppCompatActivity() {
    private lateinit var binding: ActivitySchedulePageDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchedulePageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNotify.setOnClickListener(){
            Toast.makeText(this,"Notification Created", Toast.LENGTH_SHORT).show()
        }
    }
}