package com.mlbdev.mantapluarbiasa

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mlbdev.mantapluarbiasa.MemberAdapter.MemberViewHolder
import com.mlbdev.mantapluarbiasa.databinding.ApplyteamCardBinding
import com.mlbdev.mantapluarbiasa.databinding.WhatWePlayCardsBinding


class ApplyTeamAdapter(private val ApplyTeamList:ArrayList<ApplyTeamBank>): RecyclerView.Adapter<ApplyTeamAdapter.ApplyTeamViewHolder>() {
    class ApplyTeamViewHolder(val binding: ApplyteamCardBinding):RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplyTeamViewHolder {
        val binding  = ApplyteamCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApplyTeamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ApplyTeamViewHolder, position: Int) {
        holder.binding.txtNamaGame.text = ApplyTeamList[position].nama_game
        holder.binding.txtStatus.text = ApplyTeamList[position].status
    }

    override fun getItemCount(): Int {
        return ApplyTeamList.size
    }
}