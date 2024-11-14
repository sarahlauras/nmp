package com.mlbdev.mantapluarbiasa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mlbdev.mantapluarbiasa.databinding.ActivityWhoWeAreBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WhoWeAreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WhoWeAreFragment : Fragment() {

//    private var param1: String? = null
//    private var param2: String? = null

    private var likeCount = 0
    private lateinit var binding: ActivityWhoWeAreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):
    View? {
        val gameIndex = arguments?.getInt("GAME_INDEX") ?: 0

        val filteredTeams = TeamData.teamData.filter {
            it.gameIndex == gameIndex
        }

        if(filteredTeams.isNotEmpty()){
            val team = filteredTeams[0]

            binding.imageTeam.setImageResource(team.img)
            binding.txtTeamName.text = team.name
            binding.txtDescription.text =team.description
        }
        binding.btnLike.setOnClickListener {
            likeCount++
            updateLikeButton()
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_who_we_are, container, false)


    }

    private fun updateLikeButton() {
        binding.btnLike.text = "Like: $likeCount"
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WhoWeAreFragment.
         */
        @JvmStatic
        fun newInstance(gameIndex: Int) =
            WhoWeAreFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                    putInt("GAME_INDEX", gameIndex)
                }
            }
    }
}