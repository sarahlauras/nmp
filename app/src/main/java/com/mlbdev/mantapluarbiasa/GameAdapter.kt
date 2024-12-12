package com.mlbdev.mantapluarbiasa

import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mlbdev.mantapluarbiasa.databinding.WhatWePlayCardsBinding
import com.squareup.picasso.Picasso

class GameAdapter(private val gameList: ArrayList<GameBank>) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    class GameViewHolder(val binding: WhatWePlayCardsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = WhatWePlayCardsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = gameList[position]

        Picasso.get()
            .load(game.imageId)
            .into(holder.binding.imageGame)

        holder.binding.txtTitle.text = game.name
        holder.binding.txtDesc.text = game.description
    }

    override fun getItemCount(): Int {
        return gameList.size // Menggunakan gameList yang diterima sebagai parameter di constructor
    }
}
