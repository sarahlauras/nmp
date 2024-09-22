package com.mlbdev.mantapluarbiasa

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mlbdev.mantapluarbiasa.databinding.ActivitySchedulePageDetailBinding
import com.mlbdev.mantapluarbiasa.databinding.ActivityWhoWeAreBinding

class WhoWeAreActivity : AppCompatActivity(){
    private var likeCount = 0
    private lateinit var binding: ActivityWhoWeAreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWhoWeAreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLike.setOnClickListener {
            likeCount++
            updateLikeButton()
        }
    }

    private fun updateLikeButton() {
        binding.btnLike.text = "Like: $likeCount"
    }
}