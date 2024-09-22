package com.mlbdev.mantapluarbiasa

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mlbdev.mantapluarbiasa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOurSchedule.setOnClickListener{
            val intent = Intent(this, SchedulePageDetail::class.java)
            startActivity(intent)
        }
        binding.btnWhoWeAre.setOnClickListener{
            val intent = Intent(this, WhoWeAreActivity::class.java)
            startActivity(intent)
        }
    }

}