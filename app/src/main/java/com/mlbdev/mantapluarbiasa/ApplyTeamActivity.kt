package com.mlbdev.mantapluarbiasa

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mlbdev.mantapluarbiasa.databinding.ActivityApplyTeamBinding
import com.mlbdev.mantapluarbiasa.databinding.ActivityMemberBinding

class ApplyTeamActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApplyTeamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_apply_team)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, ApplyTeamNewActivity::class.java)
            startActivity(intent)
        }
    }
}