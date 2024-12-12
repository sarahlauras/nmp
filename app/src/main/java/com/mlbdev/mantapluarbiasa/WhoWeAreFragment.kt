package com.mlbdev.mantapluarbiasa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mlbdev.mantapluarbiasa.databinding.ActivityWhoWeAreBinding
import com.mlbdev.mantapluarbiasa.databinding.FragmentWhoWeAreBinding

//private val "GAME_INDEX" = "gameIndex"

class WhoWeAreFragment : Fragment() {

    private var likeCount = 0
    private lateinit var binding: FragmentWhoWeAreBinding
    private var gameIndex: Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            gameIndex = it.getInt("GAME_INDEX", 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):
    View? {
        binding = FragmentWhoWeAreBinding.inflate(inflater, container, false)
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
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_who_we_are, container, false)
        return binding.root

    }

    private fun updateLikeButton() {
        binding.btnLike.text = "Like: $likeCount"
    }

    companion object {
        @JvmStatic
        fun newInstance(gameIndex: Int) =
            WhoWeAreFragment().apply {
                arguments = Bundle().apply {
                    putInt("GAME_INDEX", gameIndex)
                }
            }
    }
}