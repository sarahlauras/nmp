package com.mlbdev.mantapluarbiasa

import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mlbdev.mantapluarbiasa.databinding.WhatWePlayCardsBinding

class GameAdapter ():RecyclerView.Adapter<GameAdapter.GameViewHolder>(){
    class GameViewHolder(val binding: WhatWePlayCardsBinding):RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding  = WhatWePlayCardsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    //position adalah index
    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
       holder.binding.imageGame.setImageResource(GameData.gameData[position].imageId)
       holder.binding.txtTitle.text = GameData.gameData[position].title
       holder.binding.txtDesc.text = GameData.gameData[position].description

        holder.binding.btnAchievement.setOnClickListener {
            val intent = Intent(holder.itemView.context, Achievement::class.java)
            intent.putExtra("GAME_INDEX", position)
            holder.itemView.context.startActivity(intent)
        }
        holder.binding.btnTeams.setOnClickListener {
            val intent = Intent(holder.itemView.context, Teams::class.java)
            intent.putExtra("GAME_INDEX", position)
            holder.itemView.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return GameData.gameData.size //mereturn jumlah gameData yang harus ditampilkan pada rec
    }

}