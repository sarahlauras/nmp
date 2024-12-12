package com.mlbdev.mantapluarbiasa

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mlbdev.mantapluarbiasa.databinding.ActivityTeamsBinding
import com.mlbdev.mantapluarbiasa.databinding.ActivityWhatWePlayBinding
import com.mlbdev.mantapluarbiasa.databinding.TeamsCardBinding


class Teams : AppCompatActivity() {
    private lateinit var binding: ActivityTeamsBinding
    private lateinit var adapter: TeamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gameIndex = intent.getIntExtra("GAME_INDEX", 0)

        val filteredTeams = TeamData.teamData.filter { it.idgame == gameIndex }

        val teamsArray = filteredTeams.toTypedArray()

        //val gameImg = GameData.gameData[gameIndex].imageId
        //binding.imgGame.setImageResource(gameImg)

        adapter = TeamAdapter(teamsArray, gameIndex)

        binding.teamsPage.layoutManager = LinearLayoutManager(this)
        binding.teamsPage.setHasFixedSize(true)
        binding.teamsPage.adapter = adapter
    }
}