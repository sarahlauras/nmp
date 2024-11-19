package com.mlbdev.mantapluarbiasa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mlbdev.mantapluarbiasa.databinding.ActivityOurScheduleBinding
import com.mlbdev.mantapluarbiasa.databinding.ActivityWhatWePlayBinding
import com.mlbdev.mantapluarbiasa.databinding.FragmentWhatWePlayBinding
import com.mlbdev.mantapluarbiasa.databinding.FragmentWhoWeAreBinding

private val ARG_GAME = "arraygame"

class WhatWePlayFragment : Fragment() {
    private var whatwe_play: ArrayList<GameBank> = ArrayList()
    private lateinit var binding: FragmentWhatWePlayBinding
    private lateinit var listAdapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            whatwe_play = it.getParcelableArrayList<GameBank>(ARG_GAME) as ArrayList<GameBank>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWhatWePlayBinding.inflate(inflater, container, false)

        binding.recGames.layoutManager = LinearLayoutManager(requireContext())
        binding.recGames.setHasFixedSize(true)

        listAdapter = GameAdapter(whatwe_play)
        binding.recGames.adapter = listAdapter

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(whatwe_play:ArrayList<GameBank>) = WhatWePlayFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(ARG_GAME, whatwe_play)
            }
        }
    }
}