package com.mlbdev.mantapluarbiasa

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mlbdev.mantapluarbiasa.databinding.ActivityMemberBinding
import java.lang.reflect.Member

class MemberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemberBinding
    private lateinit var adapter: MemberAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gameIndex = intent.getIntExtra("GAME_INDEX", 0)
        val teamIndex = intent.getIntExtra("TEAM_INDEX", 0)

        val filteredMembers = MembersData.memberData.filter {
            it.gamesIndex == gameIndex && it.teamsIndex == teamIndex
        }

        val membersArray = filteredMembers.toTypedArray()

        val gameImg = GameData.gameData[gameIndex].imageId
        binding.imgGame.setImageResource(gameImg)

        adapter = MemberAdapter(membersArray)
        binding.membersPage.layoutManager = LinearLayoutManager(this)
        binding.membersPage.setHasFixedSize(true)
        binding.membersPage.adapter = adapter
    }
}