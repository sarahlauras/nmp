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

        val gameIndex = intent.getIntExtra("GAME_INDEX", 0)

        val filteredTeams = TeamData.teamData.filter {
            it.idgame == gameIndex
        }

        if(filteredTeams.isNotEmpty()){
            val team = filteredTeams[0]

            binding.imageTeam.setImageResource(team.image)
            binding.txtTeamName.text = team.nameteam
            binding.txtDescription.text =team.description
        }
        binding.btnLike.setOnClickListener {
            likeCount++
            updateLikeButton()
        }
    }

    private fun updateLikeButton() {
        binding.btnLike.text = "Like: $likeCount"
    }
}