package com.mlbdev.mantapluarbiasa

import android.content.Intent
import android.text.Layout
import android.util.Log
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

        holder.binding.btnAchievement.setOnClickListener {
            val intent = Intent(holder.itemView.context, Achievement::class.java)
            intent.putExtra("name", game.name) // Send gname as an extra
            holder.itemView.context.startActivity(intent)
        }

        holder.binding.btnTeams.setOnClickListener {
            val intent = Intent(holder.itemView.context, Teams::class.java)
            intent.putExtra("idgame", game.idgame.toString()) // Send gname as an extra
            intent.putExtra("image", game.imageId)
            Log.d("idgame", "${game.idgame}")
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return gameList.size // Menggunakan gameList yang diterima sebagai parameter di constructor
    }
}
